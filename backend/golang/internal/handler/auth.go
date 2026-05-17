package handler

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/service"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/utils"
)

type AuthHandler struct {
	service *service.AuthService
}

func NewAuthHandler(svc *service.AuthService) *AuthHandler {
	return &AuthHandler{service: svc}
}

type LoginRequest struct {
	Email    string `json:"email" binding:"required,email"`
	Password string `json:"password" binding:"required"`
}

type RegisterRequest struct {
	Email       string `json:"email" binding:"required,email"`
	Password    string `json:"password" binding:"required,min=6"`
	Name        string `json:"name" binding:"required"`
	PhoneNumber string `json:"phoneNumber"`
}

type PasswordResetRequest struct {
	Email string `json:"email" binding:"required,email"`
}

type PasswordResetConfirmationRequest struct {
	ResetPasswordToken string `json:"resetPasswordToken" binding:"required"`
	NewPassword        string `json:"newPassword" binding:"required,min=6"`
}

type VerificationRequest struct {
	VerificationToken string `json:"verificationToken" binding:"required"`
}

type ResetTokenRequest struct {
	ResetPasswordToken string `json:"resetPasswordToken" binding:"required"`
}

type LogoutRequest struct {
	SessionID string `json:"sessionId" binding:"required"`
}

func (h *AuthHandler) Login(c *gin.Context) {
	var req LoginRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	token, err := h.service.Login(service.LoginInput{
		Email:    req.Email,
		Password: req.Password,
	})
	if err != nil {
		status := http.StatusUnauthorized
		c.JSON(status, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"token": token})
}

func (h *AuthHandler) Logout(c *gin.Context) {
	var req LogoutRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "no session ID provided"})
		return
	}

	if err := h.service.Logout(req.SessionID); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Logged out successfully"})
}

func (h *AuthHandler) Register(c *gin.Context) {
	email := c.PostForm("email")
	password := c.PostForm("password")
	name := c.PostForm("name")
	phoneNumber := c.PostForm("phoneNumber")

	if email == "" || password == "" || name == "" {
		c.JSON(http.StatusBadRequest, gin.H{"error": "email, password, and name are required"})
		return
	}

	var avatarURL string
	if _, err := c.FormFile("avatar"); err == nil {
		uploadPath, saveErr := utils.HandleFileUpload(c, "avatar")
		if saveErr == nil {
			avatarURL = uploadPath
		}
	}

	err := h.service.Register(service.RegisterInput{
		Email:       email,
		Password:    password,
		Name:        name,
		PhoneNumber: phoneNumber,
		AvatarURL:   avatarURL,
	})
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Email sent successfully"})
}

func (h *AuthHandler) VerifyEmail(c *gin.Context) {
	var req VerificationRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.service.VerifyEmail(req.VerificationToken); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Verification successful"})
}

func (h *AuthHandler) RequestPasswordReset(c *gin.Context) {
	var req PasswordResetRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.service.RequestPasswordReset(req.Email); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Password reset link sent to email"})
}

func (h *AuthHandler) VerifyResetToken(c *gin.Context) {
	var req ResetTokenRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.service.VerifyResetToken(req.ResetPasswordToken); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Valid token"})
}

func (h *AuthHandler) ResetPassword(c *gin.Context) {
	var req PasswordResetConfirmationRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.service.ResetPassword(service.PasswordResetConfirmationInput{
		ResetPasswordToken: req.ResetPasswordToken,
		NewPassword:        req.NewPassword,
	}); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Password reset successful"})
}
