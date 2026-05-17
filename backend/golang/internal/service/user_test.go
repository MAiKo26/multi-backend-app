package service

import (
	"testing"
	"time"

	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/repository"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/testutil"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/utils"
	"gorm.io/gorm"
)

func createTestUser(t *testing.T, db *gorm.DB, email, name, role string, verified bool) *model.User {
	t.Helper()
	hashed, _ := utils.HashPassword("password123")
	user := &model.User{
		Email:      email,
		Password:   hashed,
		Name:       name,
		Role:       role,
		IsVerified: verified,
	}
	if err := db.Create(user).Error; err != nil {
		t.Fatalf("Failed to create test user: %v", err)
	}
	return user
}

func TestUserService_GetAllUsers(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	createTestUser(t, db, "user1@test.com", "User One", "user", true)
	createTestUser(t, db, "user2@test.com", "User Two", "user", true)

	users, err := svc.GetAllUsers()
	if err != nil {
		t.Fatalf("GetAllUsers() error = %v", err)
	}

	if len(users) != 2 {
		t.Errorf("Expected 2 users, got %d", len(users))
	}
}

func TestUserService_GetUserByID(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	user := createTestUser(t, db, "findme@test.com", "Find Me", "user", true)

	found, err := svc.GetUserByID(user.ID)
	if err != nil {
		t.Fatalf("GetUserByID() error = %v", err)
	}

	if found.Email != "findme@test.com" {
		t.Errorf("Expected email 'findme@test.com', got '%s'", found.Email)
	}
}

func TestUserService_GetUserByIDNotFound(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	_, err := svc.GetUserByID(999)
	if err == nil {
		t.Fatal("GetUserByID() expected error for non-existent user, got nil")
	}
}

func TestUserService_CreateUser(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	user := &model.User{
		Email:    "newuser@test.com",
		Password: "hashedpass",
		Name:     "New User",
		Role:     "user",
	}

	err := svc.CreateUser(user)
	if err != nil {
		t.Fatalf("CreateUser() error = %v", err)
	}

	if user.ID == 0 {
		t.Fatal("CreateUser() did not set user ID")
	}

	found, err := svc.GetUserByID(user.ID)
	if err != nil {
		t.Fatalf("GetUserByID() after create error = %v", err)
	}

	if found.Email != "newuser@test.com" {
		t.Errorf("Expected email 'newuser@test.com', got '%s'", found.Email)
	}
}

func TestUserService_UpdateUser(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	user := createTestUser(t, db, "update@test.com", "Old Name", "user", true)

	user.Name = "New Name"
	err := svc.UpdateUser(user)
	if err != nil {
		t.Fatalf("UpdateUser() error = %v", err)
	}

	found, _ := svc.GetUserByID(user.ID)
	if found.Name != "New Name" {
		t.Errorf("Expected name 'New Name', got '%s'", found.Name)
	}
}

func TestUserService_DeleteUser(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	user := createTestUser(t, db, "delete@test.com", "Delete Me", "user", true)

	err := svc.DeleteUser(user.ID)
	if err != nil {
		t.Fatalf("DeleteUser() error = %v", err)
	}

	_, err = svc.GetUserByID(user.ID)
	if err == nil {
		t.Fatal("GetUserByID() after delete expected error, got nil")
	}
}

func TestUserService_UpdateUserByEmail(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	createTestUser(t, db, "updateemail@test.com", "Test User", "user", true)

	err := svc.UpdateUserByEmail("updateemail@test.com", map[string]interface{}{"name": "Updated Name"})
	if err != nil {
		t.Fatalf("UpdateUserByEmail() error = %v", err)
	}

	found, _ := repo.FindByEmail("updateemail@test.com")
	if found.Name != "Updated Name" {
		t.Errorf("Expected name 'Updated Name', got '%s'", found.Name)
	}
}

func TestUserService_GetUserByEmailNotFound(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	_, err := svc.GetUserProfile("nonexistent@test.com")
	if err == nil {
		t.Fatal("GetUserByEmail() expected error for non-existent user, got nil")
	}
}

func TestUserService_GetAllUsersEmpty(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	users, err := svc.GetAllUsers()
	if err != nil {
		t.Fatalf("GetAllUsers() error = %v", err)
	}

	if len(users) != 0 {
		t.Errorf("Expected 0 users, got %d", len(users))
	}
}

func TestUserService_GetOnlineUsers(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	user := createTestUser(t, db, "online@test.com", "Online User", "user", true)

	session := model.Session{
		SessionID: "test-session-id",
		Email:     user.Email,
		ExpiresAt: time.Now().Add(1 * time.Hour),
	}
	db.Create(&session)

	emails, err := svc.GetOnlineUsers()
	if err != nil {
		t.Fatalf("GetOnlineUsers() error = %v", err)
	}

	if len(emails) != 1 {
		t.Errorf("Expected 1 online user, got %d", len(emails))
	}
}

func TestUserService_GetOnlineUsersExpiredSession(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	user := createTestUser(t, db, "expired@test.com", "Expired User", "user", true)

	session := model.Session{
		SessionID: "expired-session",
		Email:     user.Email,
		ExpiresAt: time.Now().Add(-1 * time.Hour),
	}
	db.Create(&session)

	emails, err := svc.GetOnlineUsers()
	if err != nil {
		t.Fatalf("GetOnlineUsers() error = %v", err)
	}

	if len(emails) != 0 {
		t.Errorf("Expected 0 online users with expired session, got %d", len(emails))
	}
}

func TestUserService_GetUsersByTeamID(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	user1 := createTestUser(t, db, "teamuser1@test.com", "Team User 1", "user", true)
	user2 := createTestUser(t, db, "teamuser2@test.com", "Team User 2", "user", true)

	team := model.Team{Name: "Test Team"}
	db.Create(&team)

	db.Create(&model.TeamMember{TeamID: team.ID, Email: user1.Email})
	db.Create(&model.TeamMember{TeamID: team.ID, Email: user2.Email})

	users, err := svc.GetUsersByTeamID(team.ID)
	if err != nil {
		t.Fatalf("GetUsersByTeamID() error = %v", err)
	}

	if len(users) != 2 {
		t.Errorf("Expected 2 users in team, got %d", len(users))
	}
}

func TestUserService_GetUsersByTeamIDEmpty(t *testing.T) {
	db := testutil.SetupTestDB(t)
	repo := repository.NewUserRepository(db)
	svc := NewUserService(repo, db)

	users, err := svc.GetUsersByTeamID(999)
	if err != nil {
		t.Fatalf("GetUsersByTeamID() error = %v", err)
	}

	if len(users) != 0 {
		t.Errorf("Expected 0 users in non-existent team, got %d", len(users))
	}
}
