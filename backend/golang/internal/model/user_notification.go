package model

import "time"

type UserNotification struct {
	UserID         string    `json:"userId" gorm:"primaryKey"`
	NotificationID string    `json:"notificationId" gorm:"primaryKey"`
	CreatedAt      time.Time `json:"createdAt"`

	User         User         `gorm:"foreignKey:UserID;references:Email" json:"users"`
	Notification Notification `gorm:"foreignKey:NotificationID" json:"notifications"`
}
