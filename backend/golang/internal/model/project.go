package model

import "time"

type Project struct {
	ID        string    `json:"id" gorm:"primaryKey"`
	Name      string    `json:"name" gorm:"not null"`
	TeamID    uint      `json:"teamId" gorm:"not null;index"`
	CreatedAt time.Time `json:"createdAt"`
	UpdatedAt time.Time `json:"updatedAt"`

	Team    Team            `gorm:"foreignKey:TeamID" json:"team"`
	Members []ProjectMember `gorm:"foreignKey:ProjectID" json:"projectMembers"`
	Tasks   []Task          `gorm:"foreignKey:ProjectID" json:"tasks"`
}
