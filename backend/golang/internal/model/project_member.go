package model

import "time"

type ProjectMember struct {
	ProjectID string    `json:"projectId" gorm:"primaryKey"`
	Email     string    `json:"email" gorm:"primaryKey"`
	CreatedAt time.Time `json:"createdAt"`

	Project Project `gorm:"foreignKey:ProjectID" json:"projects"`
	User    User    `gorm:"foreignKey:Email;references:Email" json:"users"`
}
