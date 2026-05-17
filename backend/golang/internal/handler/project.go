package handler

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/service"
)

type ProjectHandler struct {
	svc *service.ProjectService
}

func NewProjectHandler(svc *service.ProjectService) *ProjectHandler {
	return &ProjectHandler{svc: svc}
}

type CreateProjectRequest struct {
	Name   string `json:"name" binding:"required"`
	TeamID uint   `json:"teamId" binding:"required"`
}

type AddProjectMemberRequest struct {
	ProjectID string `json:"projectId" binding:"required"`
	Email     string `json:"email" binding:"required,email"`
}

func (h *ProjectHandler) GetAllProjects(c *gin.Context) {
	teamID := c.Param("teamId")
	projects, err := h.svc.GetProjectsByTeamID(parseUint(teamID))
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, projects)
}

func (h *ProjectHandler) CreateProject(c *gin.Context) {
	var req CreateProjectRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	project, err := h.svc.CreateProject(req.Name, req.TeamID)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	email, _ := c.Get("email")
	h.svc.LogActivity(email.(string), "Created project: "+req.Name)

	c.JSON(http.StatusCreated, project)
}

func (h *ProjectHandler) AddMember(c *gin.Context) {
	var req AddProjectMemberRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.svc.AddMember(req.ProjectID, req.Email); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Member added successfully"})
}

func (h *ProjectHandler) DeleteProject(c *gin.Context) {
	id := c.Param("projectId")
	if err := h.svc.DeleteProject(id); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"message": "Project deleted successfully"})
}

func parseUint(s string) uint {
	var n uint
	for _, c := range s {
		if c >= '0' && c <= '9' {
			n = n*10 + uint(c-'0')
		}
	}
	return n
}
