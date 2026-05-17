package v1

import (
	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/handler"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/middleware"
)

func SetupChatRoutes(r *gin.Engine, h *handler.ChatHandler) {
	r.GET("/chat/history/:roomId", middleware.AuthMiddleware(), h.GetHistoryMessages)

	r.GET("/chat", h.HandleGeneralChat)
	r.GET("/chat/:roomId", h.HandlePrivateChat)
}
