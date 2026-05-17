package repository

import (
	"errors"

	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"gorm.io/gorm"
)

type TeamRepository struct {
	db *gorm.DB
}

func NewTeamRepository(db *gorm.DB) *TeamRepository {
	return &TeamRepository{db: db}
}

func (r *TeamRepository) FindAll() ([]model.Team, error) {
	var teams []model.Team
	err := r.db.Preload("Members").Find(&teams).Error
	return teams, err
}

func (r *TeamRepository) FindByID(id uint) (*model.Team, error) {
	var team model.Team
	err := r.db.Preload("Members").First(&team, id).Error
	if err != nil {
		return nil, err
	}
	return &team, nil
}

func (r *TeamRepository) FindByUserEmail(email string) ([]model.Team, error) {
	var teams []model.Team
	err := r.db.
		Joins("JOIN team_members ON team_members.team_id = teams.id").
		Where("team_members.email = ?", email).
		Preload("Members").
		Find(&teams).Error
	return teams, err
}

func (r *TeamRepository) Create(team *model.Team) error {
	return r.db.Create(team).Error
}

func (r *TeamRepository) Update(team *model.Team) error {
	return r.db.Save(team).Error
}

func (r *TeamRepository) Delete(id uint) error {
	return r.db.Delete(&model.Team{}, id).Error
}

func (r *TeamRepository) AddMember(member *model.TeamMember) error {
	var existing model.TeamMember
	err := r.db.Where("team_id = ? AND email = ?", member.TeamID, member.Email).First(&existing).Error
	if err == nil {
		return errors.New("member already exists in team")
	}
	return r.db.Create(member).Error
}
