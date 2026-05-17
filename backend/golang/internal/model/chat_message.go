package model

import "time"

type ChatMessage struct {
	ID        uint      `json:"id" gorm:"primaryKey;autoIncrement"`
	RoomID    string    `json:"roomId" gorm:"not null;index"`
	SenderID  string    `json:"senderId" gorm:"not null;index"`
	Content   string    `json:"content" gorm:"not null"`
	ReadBy    string    `json:"readBy"`
	CreatedAt time.Time `json:"createdAt" gorm:"default:CURRENT_TIMESTAMP"`

	Room   ChatRoom `gorm:"foreignKey:RoomID" json:"chatRooms"`
	Sender User     `gorm:"foreignKey:SenderID;references:Email" json:"sender"`
}
