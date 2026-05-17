package model

import "time"

type TaskComment struct {
	ID        string    `json:"id" gorm:"primaryKey"`
	TaskID    string    `json:"taskId" gorm:"not null;index"`
	UserID    string    `json:"userId" gorm:"not null;index"`
	Content   string    `json:"content" gorm:"not null;size:255"`
	CreatedAt time.Time `json:"createdAt"`

	Task Task `gorm:"foreignKey:TaskID" json:"tasks"`
	User User `gorm:"foreignKey:UserID;references:Email" json:"users"`
}
