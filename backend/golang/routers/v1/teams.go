package v1

import (
	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/handler"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/middleware"
)

func SetupTeamRoutes(r *gin.Engine, h *handler.TeamHandler) {
	teams := r.Group("/teams")
	teams.Use(middleware.AuthMiddleware())
	{
		teams.GET("", middleware.AdminMiddleware(), h.GetAllTeams)
		teams.POST("", middleware.AdminMiddleware(), h.CreateTeam)
		teams.PUT("/:id", middleware.AdminMiddleware(), h.UpdateTeam)
		teams.DELETE("/:id", middleware.AdminMiddleware(), h.DeleteTeam)
		teams.POST("/members", middleware.AdminMiddleware(), h.AddMember)
		teams.GET("/byuser/:email", h.GetTeamsByUserEmail)
	}
}
