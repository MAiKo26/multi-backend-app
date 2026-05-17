package handler

import (
	"encoding/json"
	"log"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/gorilla/websocket"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/service"
)

type ChatHandler struct {
	svc *service.ChatService
}

func NewChatHandler(svc *service.ChatService) *ChatHandler {
	return &ChatHandler{svc: svc}
}

var upgrader = websocket.Upgrader{
	CheckOrigin: func(r *http.Request) bool {
		return true
	},
}

func (h *ChatHandler) HandleGeneralChat(c *gin.Context) {
	conn, err := upgrader.Upgrade(c.Writer, c.Request, nil)
	if err != nil {
		log.Printf("WebSocket upgrade error: %v", err)
		return
	}
	defer conn.Close()

	h.svc.InitializeRoom("general")
	h.svc.AddClient("general", conn)

	for {
		_, message, err := conn.ReadMessage()
		if err != nil {
			h.svc.RemoveClient("general", conn)
			break
		}

		var msg service.ChatMessageInput
		if err := json.Unmarshal(message, &msg); err != nil {
			log.Printf("Invalid message format: %v", err)
			continue
		}

		if err := h.svc.SaveMessage("general", msg.SenderID, msg.Content); err != nil {
			log.Printf("Error saving message: %v", err)
			continue
		}

		h.svc.Broadcast("general", conn, message)
	}
}

func (h *ChatHandler) HandlePrivateChat(c *gin.Context) {
	roomID := c.Param("roomId")

	conn, err := upgrader.Upgrade(c.Writer, c.Request, nil)
	if err != nil {
		log.Printf("WebSocket upgrade error: %v", err)
		return
	}
	defer conn.Close()

	h.svc.InitializeRoom(roomID)
	h.svc.AddClient(roomID, conn)

	for {
		_, message, err := conn.ReadMessage()
		if err != nil {
			h.svc.RemoveClient(roomID, conn)
			break
		}

		var msg service.ChatMessageInput
		if err := json.Unmarshal(message, &msg); err != nil {
			log.Printf("Invalid message format: %v", err)
			continue
		}

		if err := h.svc.SaveMessage(roomID, msg.SenderID, msg.Content); err != nil {
			log.Printf("Error saving message: %v", err)
			continue
		}

		h.svc.Broadcast(roomID, conn, message)
	}
}

func (h *ChatHandler) GetHistoryMessages(c *gin.Context) {
	roomID := c.Param("roomId")
	messages, err := h.svc.GetHistoryMessages(roomID)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, messages)
}
