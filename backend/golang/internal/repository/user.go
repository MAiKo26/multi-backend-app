package repository

import (
	"time"

	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"gorm.io/gorm"
)

type UserRepository struct {
	db *gorm.DB
}

func NewUserRepository(db *gorm.DB) *UserRepository {
	return &UserRepository{db: db}
}

func (r *UserRepository) FindAll() ([]model.User, error) {
	var users []model.User
	err := r.db.Find(&users).Error
	return users, err
}

func (r *UserRepository) FindByEmail(email string) (*model.User, error) {
	var user model.User
	err := r.db.Where("email = ?", email).First(&user).Error
	if err != nil {
		return nil, err
	}
	return &user, nil
}

func (r *UserRepository) FindByID(id uint) (*model.User, error) {
	var user model.User
	err := r.db.First(&user, id).Error
	if err != nil {
		return nil, err
	}
	return &user, nil
}

func (r *UserRepository) FindByTeamID(teamID uint) ([]model.User, error) {
	var users []model.User
	err := r.db.
		Joins("JOIN team_members ON team_members.email = users.email").
		Where("team_members.team_id = ?", teamID).
		Find(&users).Error
	return users, err
}

func (r *UserRepository) FindOnline() ([]string, error) {
	var emails []string
	now := time.Now()
	err := r.db.
		Model(&model.User{}).
		Select("users.email").
		Joins("JOIN sessions ON sessions.email = users.email").
		Where("sessions.expires_at > ?", now).
		Pluck("email", &emails).Error
	return emails, err
}

func (r *UserRepository) Create(user *model.User) error {
	return r.db.Create(user).Error
}

func (r *UserRepository) Update(user *model.User) error {
	return r.db.Save(user).Error
}

func (r *UserRepository) UpdateByEmail(email string, updates map[string]interface{}) error {
	return r.db.Model(&model.User{}).Where("email = ?", email).Updates(updates).Error
}

func (r *UserRepository) Delete(id uint) error {
	return r.db.Delete(&model.User{}, id).Error
}
