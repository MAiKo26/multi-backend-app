package service

import (
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/repository"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/utils"
	"gorm.io/gorm"
)

type UserService struct {
	repo *repository.UserRepository
	db   *gorm.DB
}

func NewUserService(repo *repository.UserRepository, db *gorm.DB) *UserService {
	return &UserService{repo: repo, db: db}
}

func (s *UserService) GetAllUsers() ([]model.User, error) {
	return s.repo.FindAll()
}

func (s *UserService) GetUserByID(id uint) (*model.User, error) {
	return s.repo.FindByID(id)
}

func (s *UserService) GetUsersByTeamID(teamID uint) ([]model.User, error) {
	return s.repo.FindByTeamID(teamID)
}

func (s *UserService) GetOnlineUsers() ([]string, error) {
	return s.repo.FindOnline()
}

func (s *UserService) CreateUser(user *model.User) error {
	return s.repo.Create(user)
}

func (s *UserService) UpdateUser(user *model.User) error {
	return s.repo.Update(user)
}

func (s *UserService) UpdateUserByEmail(email string, updates map[string]interface{}) error {
	return s.repo.UpdateByEmail(email, updates)
}

func (s *UserService) DeleteUser(id uint) error {
	return s.repo.Delete(id)
}

func (s *UserService) GetUserProfile(email string) (*model.User, error) {
	user, err := s.repo.FindByEmail(email)
	if err != nil {
		return nil, err
	}
	s.db.Model(user).Association("UserSettings").Find(&user.UserSettings)
	return user, nil
}

func (s *UserService) UpdatePassword(email, currentPassword, newPassword string) error {
	user, err := s.repo.FindByEmail(email)
	if err != nil {
		return err
	}

	if err := utils.ComparePassword(user.Password, currentPassword); err != nil {
		return err
	}

	hashed, err := utils.HashPassword(newPassword)
	if err != nil {
		return err
	}

	return s.repo.UpdateByEmail(email, map[string]interface{}{"password": hashed})
}

func (s *UserService) UpdateNotificationSettings(email string, emailNotif, pushNotif bool, emailDigest, taskReminders bool) error {
	updates := map[string]interface{}{
		"email_notifications": emailNotif,
		"push_notifications":  pushNotif,
	}
	if err := s.repo.UpdateByEmail(email, updates); err != nil {
		return err
	}

	return s.db.Model(&model.UserSettings{}).Where("user_email = ?", email).Updates(map[string]interface{}{
		"email_digest":    emailDigest,
		"task_reminders":  taskReminders,
	}).Error
}
