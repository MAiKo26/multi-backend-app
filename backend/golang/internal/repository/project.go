package repository

import (
	"errors"

	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"gorm.io/gorm"
)

type ProjectRepository struct {
	db *gorm.DB
}

func NewProjectRepository(db *gorm.DB) *ProjectRepository {
	return &ProjectRepository{db: db}
}

func (r *ProjectRepository) FindByTeamID(teamID uint) ([]model.Project, error) {
	var projects []model.Project
	err := r.db.Where("team_id = ?", teamID).Preload("Tasks").Find(&projects).Error
	return projects, err
}

func (r *ProjectRepository) FindByID(id string) (*model.Project, error) {
	var project model.Project
	err := r.db.Preload("Tasks").Preload("Members").First(&project, "id = ?", id).Error
	if err != nil {
		return nil, err
	}
	return &project, nil
}

func (r *ProjectRepository) Create(project *model.Project) error {
	return r.db.Create(project).Error
}

func (r *ProjectRepository) Delete(id string) error {
	return r.db.Delete(&model.Project{}, "id = ?", id).Error
}

func (r *ProjectRepository) AddMember(member *model.ProjectMember) error {
	var existing model.ProjectMember
	err := r.db.Where("project_id = ? AND email = ?", member.ProjectID, member.Email).First(&existing).Error
	if err == nil {
		return errors.New("member already exists in project")
	}
	return r.db.Create(member).Error
}
