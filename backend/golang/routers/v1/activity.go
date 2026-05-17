package v1

import (
	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/handler"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/middleware"
)

func SetupActivityRoutes(r *gin.Engine, h *handler.ActivityHandler) {
	activity := r.Group("/activity")
	activity.Use(middleware.AuthMiddleware())
	{
		activity.GET("", h.GetAllActivities)
		activity.GET("/user/:email", h.GetActivityByEmail)
	}
}
