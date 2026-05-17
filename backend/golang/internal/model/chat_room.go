package model

import "time"

type ChatRoom struct {
	RoomID    string    `json:"roomId" gorm:"primaryKey"`
	CreatedAt time.Time `json:"createdAt"`

	Messages []ChatMessage `gorm:"foreignKey:RoomID" json:"chatMessages"`
}
