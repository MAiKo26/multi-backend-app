package service

import (
	"log"
	"sync"

	"github.com/gorilla/websocket"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"gorm.io/gorm"
)

type ChatService struct {
	db    *gorm.DB
	rooms map[string]map[*websocket.Conn]bool
	mu    sync.RWMutex
}

func NewChatService(db *gorm.DB) *ChatService {
	return &ChatService{
		db:    db,
		rooms: make(map[string]map[*websocket.Conn]bool),
	}
}

type ChatMessageInput struct {
	RoomID    string `json:"roomId"`
	SenderID  string `json:"senderId"`
	Content   string `json:"content"`
	ReadBy    string `json:"readBy"`
	CreatedAt string `json:"createdAt"`
}

func (s *ChatService) InitializeRoom(roomID string) {
	s.mu.Lock()
	defer s.mu.Unlock()

	if _, exists := s.rooms[roomID]; !exists {
		var room model.ChatRoom
		if err := s.db.Where("room_id = ?", roomID).First(&room).Error; err != nil {
			s.db.Create(&model.ChatRoom{RoomID: roomID})
		}
		s.rooms[roomID] = make(map[*websocket.Conn]bool)
	}
}

func (s *ChatService) AddClient(roomID string, conn *websocket.Conn) {
	s.mu.Lock()
	defer s.mu.Unlock()

	if _, exists := s.rooms[roomID]; !exists {
		s.rooms[roomID] = make(map[*websocket.Conn]bool)
	}
	s.rooms[roomID][conn] = true
}

func (s *ChatService) RemoveClient(roomID string, conn *websocket.Conn) {
	s.mu.Lock()
	defer s.mu.Unlock()

	if clients, exists := s.rooms[roomID]; exists {
		delete(clients, conn)
		if len(clients) == 0 {
			delete(s.rooms, roomID)
		}
	}
}

func (s *ChatService) Broadcast(roomID string, sender *websocket.Conn, message []byte) {
	s.mu.RLock()
	defer s.mu.RUnlock()

	if clients, exists := s.rooms[roomID]; exists {
		for client := range clients {
			if client != sender {
				err := client.WriteMessage(websocket.TextMessage, message)
				if err != nil {
					log.Printf("WebSocket write error: %v", err)
					client.Close()
					delete(clients, client)
				}
			}
		}
	}
}

func (s *ChatService) SaveMessage(roomID, senderID, content string) error {
	msg := model.ChatMessage{
		RoomID:   roomID,
		SenderID: senderID,
		Content:  content,
	}
	return s.db.Create(&msg).Error
}

func (s *ChatService) GetHistoryMessages(roomID string) ([]model.ChatMessage, error) {
	var messages []model.ChatMessage
	err := s.db.Where("room_id = ?", roomID).Order("created_at ASC").Find(&messages).Error
	return messages, err
}
