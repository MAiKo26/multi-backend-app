package service

import (
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/repository"
)

type ActivityService struct {
	repo *repository.ActivityRepository
}

func NewActivityService(repo *repository.ActivityRepository) *ActivityService {
	return &ActivityService{repo: repo}
}

func (s *ActivityService) GetAllActivities() ([]model.ActivityHistory, error) {
	return s.repo.FindAll()
}

func (s *ActivityService) GetActivitiesByEmail(email string) ([]model.ActivityHistory, error) {
	return s.repo.FindByUserEmail(email)
}
