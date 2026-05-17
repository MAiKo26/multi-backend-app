package model

import "time"

type Session struct {
	SessionID string    `json:"sessionId" gorm:"primaryKey"`
	Email     string    `json:"email" gorm:"not null;index"`
	CreatedAt time.Time `json:"createdAt"`
	ExpiresAt time.Time `json:"expiresAt"`

	User User `gorm:"foreignKey:Email;references:Email" json:"users"`
}
