package v1

import (
	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/handler"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/middleware"
)

func SetupProfileRoutes(r *gin.Engine, h *handler.ProfileHandler) {
	profile := r.Group("/profile")
	profile.Use(middleware.AuthMiddleware())
	{
		profile.GET("", h.GetProfile)
		profile.PUT("", h.UpdateProfile)
		profile.PUT("/password", h.UpdatePassword)
		profile.PUT("/settings", h.UpdateSettings)
	}

	r.GET("/users/bysession/:session", middleware.AuthMiddleware(), h.GetProfileBySession)
}
