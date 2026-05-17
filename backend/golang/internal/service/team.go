package service

import (
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/repository"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/utils"
	"gorm.io/gorm"
)

type TeamService struct {
	repo *repository.TeamRepository
	db   *repository.UserRepository
	gdb  *gorm.DB
}

func NewTeamService(repo *repository.TeamRepository, userRepo *repository.UserRepository, gdb *gorm.DB) *TeamService {
	return &TeamService{repo: repo, db: userRepo, gdb: gdb}
}

func (s *TeamService) GetAllTeams() ([]model.Team, error) {
	return s.repo.FindAll()
}

func (s *TeamService) GetTeamByID(id uint) (*model.Team, error) {
	return s.repo.FindByID(id)
}

func (s *TeamService) GetTeamsByUserEmail(email string) ([]model.Team, error) {
	return s.repo.FindByUserEmail(email)
}

func (s *TeamService) CreateTeam(name string, memberEmails []string) (*model.Team, error) {
	team := &model.Team{Name: name}
	if err := s.repo.Create(team); err != nil {
		return nil, err
	}

	for _, email := range memberEmails {
		member := model.TeamMember{TeamID: team.ID, Email: email}
		s.repo.AddMember(&member)
	}

	return team, nil
}

func (s *TeamService) UpdateTeam(id uint, name string) (*model.Team, error) {
	team, err := s.repo.FindByID(id)
	if err != nil {
		return nil, err
	}
	team.Name = name
	if err := s.repo.Update(team); err != nil {
		return nil, err
	}
	return team, nil
}

func (s *TeamService) DeleteTeam(id uint) error {
	return s.repo.Delete(id)
}

func (s *TeamService) AddMember(teamID uint, email string) error {
	if _, err := s.db.FindByEmail(email); err != nil {
		return err
	}
	return s.repo.AddMember(&model.TeamMember{TeamID: teamID, Email: email})
}

func (s *TeamService) LogActivity(email, description string) {
	utils.LogActivity(s.gdb, email, description)
}
