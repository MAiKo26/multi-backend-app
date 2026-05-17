package model

import "time"

type ActivityHistory struct {
	ID          string    `json:"id" gorm:"primaryKey"`
	UserID      string    `json:"userId" gorm:"not null;index"`
	Description string    `json:"description" gorm:"not null"`
	DoneAt      time.Time `json:"doneAt" gorm:"default:CURRENT_TIMESTAMP"`

	User User `gorm:"foreignKey:UserID;references:Email" json:"users"`
}
