package model

import "time"

type UserSettings struct {
	UserEmail      string    `json:"userEmail" gorm:"primaryKey"`
	EmailDigest    bool      `json:"emailDigest" gorm:"default:true"`
	TaskReminders  bool      `json:"taskReminders" gorm:"default:true"`
	CreatedAt      time.Time `json:"createdAt"`
	UpdatedAt      time.Time `json:"updatedAt"`

	User User `gorm:"foreignKey:UserEmail;references:Email" json:"users"`
}
