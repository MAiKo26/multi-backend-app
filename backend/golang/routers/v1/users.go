package v1

import (
	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/handler"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/middleware"
)

func SetupUserRoutes(r *gin.Engine, h *handler.UserHandler) {
	users := r.Group("/users")
	users.Use(middleware.AuthMiddleware())
	{
		users.GET("", h.GetUsers)
		users.GET("/byteam/:teamId", h.GetUsersByTeam)
		users.GET("/online", h.GetOnlineUsers)
		users.GET("/:id", h.GetUser)
		users.POST("", h.CreateUser)
		users.PATCH("/:id", h.UpdateUser)
		users.DELETE("/:id", h.DeleteUser)
	}
}
