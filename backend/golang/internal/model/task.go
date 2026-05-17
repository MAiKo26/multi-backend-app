package model

import "time"

type Task struct {
	ID          string     `json:"id" gorm:"primaryKey"`
	Name        string     `json:"name" gorm:"not null"`
	Description string     `json:"description"`
	ProjectID   string     `json:"projectId" gorm:"not null;index"`
	Stars       int        `json:"stars" gorm:"default:0"`
	Finished    bool       `json:"finished" gorm:"default:false"`
	FinishedBy  string     `json:"finishedBy"`
	CreatedAt   time.Time  `json:"createdAt"`
	UpdatedAt   time.Time  `json:"updatedAt"`

	Project   Project       `gorm:"foreignKey:ProjectID" json:"project"`
	Comments  []TaskComment `gorm:"foreignKey:TaskID" json:"taskComments"`
	StarredBy []StarredTask `gorm:"foreignKey:TaskID" json:"starredTasks"`
}
