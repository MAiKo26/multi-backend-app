package v1

import (
	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/handler"
)

func SetupAuthRoutes(r *gin.Engine, h *handler.AuthHandler) {
	auth := r.Group("/auth")
	{
		auth.POST("/register", h.Register)
		auth.POST("/login", h.Login)
		auth.POST("/logout", h.Logout)
		auth.POST("/register/verification", h.VerifyEmail)
		auth.POST("/password-reset", h.RequestPasswordReset)
		auth.POST("/password-reset/verification", h.VerifyResetToken)
		auth.POST("/password-reset/confirmation", h.ResetPassword)
	}
}
