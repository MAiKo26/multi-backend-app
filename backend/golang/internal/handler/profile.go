package handler

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/service"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/utils"
)

type ProfileHandler struct {
	svc *service.UserService
}

func NewProfileHandler(svc *service.UserService) *ProfileHandler {
	return &ProfileHandler{svc: svc}
}

type UpdateProfileRequest struct {
	Name        string `json:"name"`
	PhoneNumber string `json:"phoneNumber"`
	AvatarURL   string `json:"avatarUrl"`
}

type UpdatePasswordRequest struct {
	CurrentPassword string `json:"currentPassword" binding:"required"`
	NewPassword     string `json:"newPassword" binding:"required,min=6"`
}

type UpdateSettingsRequest struct {
	EmailNotifications *bool `json:"emailNotifications"`
	PushNotifications  *bool `json:"pushNotifications"`
	EmailDigest        *bool `json:"emailDigest"`
	TaskReminders      *bool `json:"taskReminders"`
}

func (h *ProfileHandler) GetProfile(c *gin.Context) {
	email, _ := c.Get("email")
	user, err := h.svc.GetUserProfile(email.(string))
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "user not found"})
		return
	}
	c.JSON(http.StatusOK, user)
}

func (h *ProfileHandler) UpdateProfile(c *gin.Context) {
	email, _ := c.Get("email")

	name := c.PostForm("name")
	phoneNumber := c.PostForm("phoneNumber")

	var avatarURL string
	if _, err := c.FormFile("avatar"); err == nil {
		uploadPath, saveErr := utils.HandleFileUpload(c, "avatar")
		if saveErr == nil {
			avatarURL = uploadPath
		}
	}

	updates := map[string]interface{}{}
	if name != "" {
		updates["name"] = name
	}
	if phoneNumber != "" {
		updates["phone_number"] = phoneNumber
	}
	if avatarURL != "" {
		updates["avatar_url"] = avatarURL
	}

	if len(updates) == 0 {
		user, _ := h.svc.GetUserProfile(email.(string))
		c.JSON(http.StatusOK, user)
		return
	}

	if err := h.svc.UpdateUserByEmail(email.(string), updates); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	user, _ := h.svc.GetUserProfile(email.(string))
	c.JSON(http.StatusOK, user)
}

func (h *ProfileHandler) UpdatePassword(c *gin.Context) {
	email, _ := c.Get("email")
	var req UpdatePasswordRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.svc.UpdatePassword(email.(string), req.CurrentPassword, req.NewPassword); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "invalid current password"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Password updated successfully"})
}

func (h *ProfileHandler) UpdateSettings(c *gin.Context) {
	email, _ := c.Get("email")
	var req UpdateSettingsRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	user, _ := h.svc.GetUserProfile(email.(string))
	emailNotif := user.EmailNotifications
	pushNotif := user.PushNotifications
	emailDigest := true
	taskReminders := true
	if user.UserSettings != nil {
		emailDigest = user.UserSettings.EmailDigest
		taskReminders = user.UserSettings.TaskReminders
	}

	if req.EmailNotifications != nil {
		emailNotif = *req.EmailNotifications
	}
	if req.PushNotifications != nil {
		pushNotif = *req.PushNotifications
	}
	if req.EmailDigest != nil {
		emailDigest = *req.EmailDigest
	}
	if req.TaskReminders != nil {
		taskReminders = *req.TaskReminders
	}

	if err := h.svc.UpdateNotificationSettings(email.(string), emailNotif, pushNotif, emailDigest, taskReminders); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Settings updated successfully"})
}

func (h *ProfileHandler) GetProfileBySession(c *gin.Context) {
	sessionID := c.Param("session")
	claims, err := utils.ValidateToken(sessionID)
	if err != nil {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "invalid session"})
		return
	}

	user, err := h.svc.GetUserProfile(claims.Email)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "user not found"})
		return
	}
	c.JSON(http.StatusOK, user)
}
