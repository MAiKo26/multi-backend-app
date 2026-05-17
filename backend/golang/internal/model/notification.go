package model

import "time"

type Notification struct {
	ID        string    `json:"id" gorm:"primaryKey"`
	Type      string    `json:"type" gorm:"not null"`
	Title     string    `json:"title" gorm:"not null"`
	Content   string    `json:"content"`
	Read      bool      `json:"read" gorm:"default:false"`
	Link      string    `json:"link"`
	CreatedAt time.Time `json:"createdAt"`
	UpdatedAt time.Time `json:"updatedAt"`

	Users []UserNotification `gorm:"foreignKey:NotificationID" json:"userNotifications"`
}
