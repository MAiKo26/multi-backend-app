package repository

import (
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"gorm.io/gorm"
)

type ActivityRepository struct {
	db *gorm.DB
}

func NewActivityRepository(db *gorm.DB) *ActivityRepository {
	return &ActivityRepository{db: db}
}

func (r *ActivityRepository) FindAll() ([]model.ActivityHistory, error) {
	var activities []model.ActivityHistory
	err := r.db.Order("done_at DESC").Find(&activities).Error
	return activities, err
}

func (r *ActivityRepository) FindByUserEmail(email string) ([]model.ActivityHistory, error) {
	var activities []model.ActivityHistory
	err := r.db.Where("user_id = ?", email).Order("done_at DESC").Find(&activities).Error
	return activities, err
}
