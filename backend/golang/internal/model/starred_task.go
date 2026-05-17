package model

import "time"

type StarredTask struct {
	UserID    string    `json:"userId" gorm:"primaryKey"`
	TaskID    string    `json:"taskId" gorm:"primaryKey"`
	CreatedAt time.Time `json:"createdAt"`

	User User `gorm:"foreignKey:UserID;references:Email" json:"users"`
	Task Task `gorm:"foreignKey:TaskID" json:"tasks"`
}
