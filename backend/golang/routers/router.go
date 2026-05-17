package routers

import (
	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/handler"
	"github.com/maiko26/multi-backend-app/backend/golang/routers/v1"
)

func Setup(r *gin.Engine, userHandler *handler.UserHandler, authHandler *handler.AuthHandler, teamHandler *handler.TeamHandler, projectHandler *handler.ProjectHandler, taskHandler *handler.TaskHandler, profileHandler *handler.ProfileHandler, activityHandler *handler.ActivityHandler, chatHandler *handler.ChatHandler) {
	v1.SetupUserRoutes(r, userHandler)
	v1.SetupAuthRoutes(r, authHandler)
	v1.SetupTeamRoutes(r, teamHandler)
	v1.SetupProjectRoutes(r, projectHandler)
	v1.SetupTaskRoutes(r, taskHandler)
	v1.SetupProfileRoutes(r, profileHandler)
	v1.SetupActivityRoutes(r, activityHandler)
	v1.SetupChatRoutes(r, chatHandler)
}
