package utils

import (
	"log"

	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"github.com/google/uuid"
	"gorm.io/gorm"
)

func LogActivity(db *gorm.DB, email, description string) {
	activity := model.ActivityHistory{
		ID:          uuid.New().String()[:8],
		UserID:      email,
		Description: description,
	}
	if err := db.Create(&activity).Error; err != nil {
		log.Printf("Failed to log activity: %v", err)
	}
}
