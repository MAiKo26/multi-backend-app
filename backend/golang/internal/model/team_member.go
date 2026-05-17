package model

import "time"

type TeamMember struct {
	TeamID    uint      `json:"teamId" gorm:"primaryKey"`
	Email     string    `json:"email" gorm:"primaryKey"`
	CreatedAt time.Time `json:"createdAt"`

	Team Team `gorm:"foreignKey:TeamID" json:"teams"`
	User User `gorm:"foreignKey:Email;references:Email" json:"users"`
}
