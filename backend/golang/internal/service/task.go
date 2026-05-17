package service

import (
	"github.com/google/uuid"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/repository"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/utils"
	"gorm.io/gorm"
)

type TaskService struct {
	repo *repository.TaskRepository
	db   *gorm.DB
}

func NewTaskService(repo *repository.TaskRepository, db *gorm.DB) *TaskService {
	return &TaskService{repo: repo, db: db}
}

func (s *TaskService) GetAllTasks() ([]model.Task, error) {
	return s.repo.FindAll()
}

func (s *TaskService) GetTasksByProjectID(projectID string) ([]model.Task, error) {
	return s.repo.FindByProjectID(projectID)
}

func (s *TaskService) CreateTask(name, description, projectID string) (*model.Task, error) {
	task := &model.Task{
		ID:          uuid.New().String()[:8],
		Name:        name,
		Description: description,
		ProjectID:   projectID,
	}
	if err := s.repo.Create(task); err != nil {
		return nil, err
	}
	return task, nil
}

func (s *TaskService) UpdateTask(id string, name, description string, finished bool, finishedBy string) (*model.Task, error) {
	task, err := s.repo.FindByID(id)
	if err != nil {
		return nil, err
	}
	if name != "" {
		task.Name = name
	}
	if description != "" {
		task.Description = description
	}
	task.Finished = finished
	if finished && finishedBy != "" {
		task.FinishedBy = finishedBy
	}
	if err := s.repo.Update(task); err != nil {
		return nil, err
	}
	return task, nil
}

func (s *TaskService) DeleteTask(id string) error {
	return s.repo.Delete(id)
}

func (s *TaskService) AddComment(taskID, userID, content string) (*model.TaskComment, error) {
	comment := &model.TaskComment{
		ID:      uuid.New().String()[:8],
		TaskID:  taskID,
		UserID:  userID,
		Content: content,
	}
	if err := s.repo.AddComment(comment); err != nil {
		return nil, err
	}
	return comment, nil
}

func (s *TaskService) ToggleStar(userID, taskID string) (bool, error) {
	return s.repo.ToggleStar(userID, taskID)
}

func (s *TaskService) LogActivity(email, description string) {
	utils.LogActivity(s.db, email, description)
}
