package v1

import (
	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/handler"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/middleware"
)

func SetupProjectRoutes(r *gin.Engine, h *handler.ProjectHandler) {
	projects := r.Group("/projects")
	projects.Use(middleware.AuthMiddleware())
	{
		projects.GET("/:teamId", h.GetAllProjects)
		projects.POST("", h.CreateProject)
		projects.POST("/members", h.AddMember)
		projects.DELETE("/:projectId", h.DeleteProject)
	}
}
