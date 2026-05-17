package service

import (
	"github.com/google/uuid"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/repository"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/utils"
	"gorm.io/gorm"
)

type ProjectService struct {
	repo *repository.ProjectRepository
	db   *repository.UserRepository
	gdb  *gorm.DB
}

func NewProjectService(repo *repository.ProjectRepository, userRepo *repository.UserRepository, gdb *gorm.DB) *ProjectService {
	return &ProjectService{repo: repo, db: userRepo, gdb: gdb}
}

func (s *ProjectService) GetProjectsByTeamID(teamID uint) ([]model.Project, error) {
	return s.repo.FindByTeamID(teamID)
}

func (s *ProjectService) CreateProject(name string, teamID uint) (*model.Project, error) {
	project := &model.Project{
		ID:     uuid.New().String()[:8],
		Name:   name,
		TeamID: teamID,
	}
	if err := s.repo.Create(project); err != nil {
		return nil, err
	}
	return project, nil
}

func (s *ProjectService) AddMember(projectID, email string) error {
	if _, err := s.db.FindByEmail(email); err != nil {
		return err
	}
	return s.repo.AddMember(&model.ProjectMember{ProjectID: projectID, Email: email})
}

func (s *ProjectService) DeleteProject(id string) error {
	return s.repo.Delete(id)
}

func (s *ProjectService) LogActivity(email, description string) {
	utils.LogActivity(s.gdb, email, description)
}
