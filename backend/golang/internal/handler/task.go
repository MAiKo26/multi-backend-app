package handler

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/service"
)

type TaskHandler struct {
	svc *service.TaskService
}

func NewTaskHandler(svc *service.TaskService) *TaskHandler {
	return &TaskHandler{svc: svc}
}

type CreateTaskRequest struct {
	Name        string `json:"name" binding:"required"`
	Description string `json:"description"`
	ProjectID   string `json:"projectId" binding:"required"`
}

type UpdateTaskRequest struct {
	Name        string `json:"name"`
	Description string `json:"description"`
	Finished    *bool  `json:"finished"`
}

type AddCommentRequest struct {
	Content string `json:"content" binding:"required,max=255"`
}

func (h *TaskHandler) GetAllTasks(c *gin.Context) {
	tasks, err := h.svc.GetAllTasks()
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, tasks)
}

func (h *TaskHandler) GetTasksByProject(c *gin.Context) {
	projectID := c.Param("projectId")
	tasks, err := h.svc.GetTasksByProjectID(projectID)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, tasks)
}

func (h *TaskHandler) CreateTask(c *gin.Context) {
	var req CreateTaskRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	task, err := h.svc.CreateTask(req.Name, req.Description, req.ProjectID)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	email, _ := c.Get("email")
	h.svc.LogActivity(email.(string), "Created task: "+req.Name)

	c.JSON(http.StatusCreated, task)
}

func (h *TaskHandler) UpdateTask(c *gin.Context) {
	id := c.Param("taskId")
	var req UpdateTaskRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	finished := false
	if req.Finished != nil {
		finished = *req.Finished
	}

	email, _ := c.Get("email")
	finishedBy := ""
	if finished {
		finishedBy = email.(string)
	}

	task, err := h.svc.UpdateTask(id, req.Name, req.Description, finished, finishedBy)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, task)
}

func (h *TaskHandler) DeleteTask(c *gin.Context) {
	id := c.Param("taskId")
	if err := h.svc.DeleteTask(id); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"message": "Task deleted successfully"})
}

func (h *TaskHandler) AddComment(c *gin.Context) {
	taskID := c.Param("taskId")
	var req AddCommentRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	email, _ := c.Get("email")
	comment, err := h.svc.AddComment(taskID, email.(string), req.Content)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	h.svc.LogActivity(email.(string), "Added comment to task")

	c.JSON(http.StatusCreated, comment)
}

func (h *TaskHandler) ToggleStar(c *gin.Context) {
	taskID := c.Param("taskId")
	email, _ := c.Get("email")

	_, err := h.svc.ToggleStar(email.(string), taskID)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	h.svc.LogActivity(email.(string), "Toggled star on task")

	c.JSON(http.StatusOK, gin.H{"message": "Task starred"})
}
