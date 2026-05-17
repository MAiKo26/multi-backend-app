package service

import (
	"errors"
	"time"

	"github.com/google/uuid"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/utils"
	"golang.org/x/crypto/bcrypt"
	"gorm.io/gorm"
)

type AuthService struct {
	db          *gorm.DB
	emailSender *utils.EmailSender
}

func NewAuthService(db *gorm.DB, emailSender *utils.EmailSender) *AuthService {
	return &AuthService{db: db, emailSender: emailSender}
}

type LoginInput struct {
	Email    string `json:"email"`
	Password string `json:"password"`
}

type RegisterInput struct {
	Email       string `json:"email"`
	Password    string `json:"password"`
	Name        string `json:"name"`
	PhoneNumber string `json:"phone_number"`
	AvatarURL   string `json:"avatar_url"`
}

type PasswordResetInput struct {
	Email string `json:"email"`
}

type PasswordResetConfirmationInput struct {
	ResetPasswordToken string `json:"reset_password_token"`
	NewPassword        string `json:"new_password"`
}

func (s *AuthService) Login(input LoginInput) (string, error) {
	var user model.User
	if err := s.db.Where("email = ?", input.Email).First(&user).Error; err != nil {
		return "", errors.New("invalid email")
	}

	now := time.Now()
	if user.AccountLockedUntil != nil && user.AccountLockedUntil.After(now) {
		return "", errors.New("account locked, try again later")
	}

	if err := bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(input.Password)); err != nil {
		user.FailedLoginAttempts++
		s.db.Model(&user).Update("failed_login_attempts", user.FailedLoginAttempts)

		if user.FailedLoginAttempts >= 5 {
			lockUntil := now.Add(15 * time.Minute)
			s.db.Model(&user).Updates(map[string]interface{}{
				"account_locked_until":  lockUntil,
				"failed_login_attempts": 0,
			})
			return "", errors.New("invalid login attempt. please try again later")
		}

		return "", errors.New("invalid password, try again")
	}

	if !user.IsVerified {
		return "", errors.New("email not verified, check your email")
	}

	var session model.Session
	err := s.db.Where("email = ?", user.Email).First(&session).Error
	if err == nil && session.ExpiresAt.After(time.Now()) {
		return session.SessionID, nil
	}

	token, err := utils.GenerateToken(user.Email, user.Role)
	if err != nil {
		return "", errors.New("failed to generate token")
	}

	newSession := model.Session{
		SessionID: token,
		Email:     user.Email,
		CreatedAt: time.Now(),
		ExpiresAt: time.Now().Add(1 * time.Hour),
	}
	s.db.Create(&newSession)

	s.db.Model(&user).Update("last_login", time.Now())

	utils.LogActivity(s.db, user.Email, "Logged in")

	return token, nil
}

func (s *AuthService) Logout(sessionID string) error {
	claims, _ := utils.ValidateToken(sessionID)

	s.db.Where("session_id = ?", sessionID).Delete(&model.Session{})

	if claims != nil {
		utils.LogActivity(s.db, claims.Email, "Logged out")
	}

	return nil
}

func (s *AuthService) Register(input RegisterInput) error {
	var existingUser model.User
	if err := s.db.Where("email = ?", input.Email).First(&existingUser).Error; err == nil {
		return errors.New("email already exists")
	}

	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(input.Password), 10)
	if err != nil {
		return errors.New("failed to hash password")
	}

	verificationToken := uuid.New().String()

	user := model.User{
		Email:             input.Email,
		Password:          string(hashedPassword),
		Name:              input.Name,
		AvatarURL:         input.AvatarURL,
		PhoneNumber:       input.PhoneNumber,
		VerificationToken: verificationToken,
		IsVerified:        false,
	}
	if err := s.db.Create(&user).Error; err != nil {
		return errors.New("failed to create user")
	}

	settings := model.UserSettings{
		UserEmail:     input.Email,
		EmailDigest:   true,
		TaskReminders: true,
	}
	s.db.Create(&settings)

	if err := s.emailSender.SendVerificationEmail(input.Email, verificationToken); err != nil {
		return errors.New("failed to send verification email")
	}

	return nil
}

func (s *AuthService) VerifyEmail(token string) error {
	var user model.User
	if err := s.db.Where("verification_token = ?", token).First(&user).Error; err != nil {
		return errors.New("invalid verification token")
	}

	s.db.Model(&user).Updates(map[string]interface{}{
		"is_verified":        true,
		"verification_token": "",
	})

	utils.LogActivity(s.db, user.Email, "Verified email and joined the platform")

	return nil
}

func (s *AuthService) RequestPasswordReset(email string) error {
	var user model.User
	if err := s.db.Where("email = ?", email).First(&user).Error; err != nil {
		return errors.New("email not found")
	}

	resetToken := uuid.New().String()
	resetExpiry := time.Now().Add(1 * time.Hour)

	s.db.Model(&user).Updates(map[string]interface{}{
		"reset_password_token":  resetToken,
		"reset_password_expiry": resetExpiry,
	})

	if err := s.emailSender.SendPasswordResetEmail(email, resetToken); err != nil {
		return errors.New("failed to send reset email")
	}

	return nil
}

func (s *AuthService) VerifyResetToken(token string) error {
	var user model.User
	now := time.Now()

	err := s.db.Where("reset_password_token = ? AND reset_password_expiry > ?", token, now).First(&user).Error
	if err != nil {
		return errors.New("invalid or expired reset token")
	}

	return nil
}

func (s *AuthService) ResetPassword(input PasswordResetConfirmationInput) error {
	var user model.User
	if err := s.db.Where("reset_password_token = ?", input.ResetPasswordToken).First(&user).Error; err != nil {
		return errors.New("invalid reset token")
	}

	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(input.NewPassword), 10)
	if err != nil {
		return errors.New("failed to hash password")
	}

	s.db.Model(&user).Update("password", string(hashedPassword))
	s.db.Model(&user).Updates(map[string]interface{}{
		"reset_password_token":  "",
		"reset_password_expiry": nil,
	})

	return nil
}
