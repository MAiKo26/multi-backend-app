package repository

import (
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"gorm.io/gorm"
)

type TaskRepository struct {
	db *gorm.DB
}

func NewTaskRepository(db *gorm.DB) *TaskRepository {
	return &TaskRepository{db: db}
}

func (r *TaskRepository) FindAll() ([]model.Task, error) {
	var tasks []model.Task
	err := r.db.Preload("Comments").Preload("StarredBy").Find(&tasks).Error
	return tasks, err
}

func (r *TaskRepository) FindByProjectID(projectID string) ([]model.Task, error) {
	var tasks []model.Task
	err := r.db.Where("project_id = ?", projectID).Preload("Comments.User").Preload("StarredBy").Find(&tasks).Error
	return tasks, err
}

func (r *TaskRepository) FindByID(id string) (*model.Task, error) {
	var task model.Task
	err := r.db.Preload("Comments").Preload("StarredBy").First(&task, "id = ?", id).Error
	if err != nil {
		return nil, err
	}
	return &task, nil
}

func (r *TaskRepository) Create(task *model.Task) error {
	return r.db.Create(task).Error
}

func (r *TaskRepository) Update(task *model.Task) error {
	return r.db.Save(task).Error
}

func (r *TaskRepository) Delete(id string) error {
	return r.db.Delete(&model.Task{}, "id = ?", id).Error
}

func (r *TaskRepository) AddComment(comment *model.TaskComment) error {
	return r.db.Create(comment).Error
}

func (r *TaskRepository) ToggleStar(userID, taskID string) (bool, error) {
	var existing model.StarredTask
	err := r.db.Where("user_id = ? AND task_id = ?", userID, taskID).First(&existing).Error
	if err == nil {
		r.db.Delete(&existing)
		return false, nil
	}
	star := model.StarredTask{UserID: userID, TaskID: taskID}
	r.db.Create(&star)
	return true, nil
}
