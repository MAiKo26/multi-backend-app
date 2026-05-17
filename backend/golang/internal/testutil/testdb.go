package testutil

import (
	"testing"

	"github.com/glebarez/sqlite"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

func SetupTestDB(t *testing.T) *gorm.DB {
	t.Helper()

	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{
		Logger: logger.Discard,
	})
	if err != nil {
		t.Fatalf("Failed to connect to test database: %v", err)
	}

	err = db.AutoMigrate(
		&model.User{},
		&model.UserSettings{},
		&model.Session{},
		&model.Team{},
		&model.TeamMember{},
		&model.Project{},
		&model.ProjectMember{},
		&model.Task{},
		&model.TaskComment{},
		&model.StarredTask{},
		&model.Notification{},
		&model.UserNotification{},
		&model.ActivityHistory{},
		&model.ChatRoom{},
		&model.ChatMessage{},
	)
	if err != nil {
		t.Fatalf("Failed to migrate test database: %v", err)
	}

	return db
}
