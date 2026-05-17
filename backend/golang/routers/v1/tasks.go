package v1

import (
	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/handler"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/middleware"
)

func SetupTaskRoutes(r *gin.Engine, h *handler.TaskHandler) {
	tasks := r.Group("/tasks")
	tasks.Use(middleware.AuthMiddleware())
	{
		tasks.GET("", h.GetAllTasks)
		tasks.GET("/project/:projectId", h.GetTasksByProject)
		tasks.POST("", h.CreateTask)
		tasks.PUT("/:taskId", h.UpdateTask)
		tasks.DELETE("/:taskId", h.DeleteTask)
		tasks.POST("/comments/:taskId", h.AddComment)
		tasks.POST("/star/:taskId", h.ToggleStar)
	}
}
