package model

import "time"

type Team struct {
	ID        uint      `json:"id" gorm:"primaryKey;autoIncrement"`
	Name      string    `json:"name" gorm:"not null"`
	CreatedAt time.Time `json:"createdAt"`
	UpdatedAt time.Time `json:"updatedAt"`

	Members  []TeamMember `gorm:"foreignKey:TeamID" json:"teamMembers"`
	Projects []Project    `gorm:"foreignKey:TeamID" json:"projects"`
}
