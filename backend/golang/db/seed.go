package db

import (
	"log"
	"time"

	"github.com/google/uuid"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"golang.org/x/crypto/bcrypt"
)

func Seed(database *DB) {
	var userCount int64
	database.DB.Model(&model.User{}).Count(&userCount)
	if userCount > 0 {
		log.Println("Database already seeded, skipping...")
		return
	}

	log.Println("Seeding database...")

	hashedPassword, err := bcrypt.GenerateFromPassword([]byte("123456789"), 10)
	if err != nil {
		log.Fatalf("Failed to hash password: %v", err)
	}
	passwordStr := string(hashedPassword)

	users := []model.User{
		{Email: "admin@example.com", Password: passwordStr, Name: "Admin User", Role: "admin", IsVerified: true},
		{Email: "alice@example.com", Password: passwordStr, Name: "Alice Smith", Role: "user", IsVerified: true},
		{Email: "bob@example.com", Password: passwordStr, Name: "Bob Johnson", Role: "user", IsVerified: true},
		{Email: "charlie@example.com", Password: passwordStr, Name: "Charlie Brown", Role: "user", IsVerified: true},
		{Email: "diana@example.com", Password: passwordStr, Name: "Diana Prince", Role: "user", IsVerified: true},
	}
	for i := range users {
		users[i].EmailNotifications = true
		users[i].PushNotifications = true
		users[i].SubscriptionStatus = "free"
	}
	database.DB.Create(&users)

	settings := []model.UserSettings{
		{UserEmail: "admin@example.com", EmailDigest: true, TaskReminders: true},
		{UserEmail: "alice@example.com", EmailDigest: true, TaskReminders: true},
		{UserEmail: "bob@example.com", EmailDigest: true, TaskReminders: true},
		{UserEmail: "charlie@example.com", EmailDigest: true, TaskReminders: true},
		{UserEmail: "diana@example.com", EmailDigest: true, TaskReminders: true},
	}
	database.DB.Create(&settings)

	teams := []model.Team{
		{Name: "Frontend"},
		{Name: "Backend"},
		{Name: "DevOps"},
		{Name: "QA"},
	}
	database.DB.Create(&teams)

	teamMembers := []model.TeamMember{
		{TeamID: teams[0].ID, Email: "alice@example.com"},
		{TeamID: teams[0].ID, Email: "bob@example.com"},
		{TeamID: teams[1].ID, Email: "charlie@example.com"},
		{TeamID: teams[1].ID, Email: "diana@example.com"},
		{TeamID: teams[2].ID, Email: "bob@example.com"},
		{TeamID: teams[3].ID, Email: "alice@example.com"},
		{TeamID: teams[3].ID, Email: "charlie@example.com"},
	}
	database.DB.Create(&teamMembers)

	projects := []model.Project{
		{ID: uuid.New().String()[:8], Name: "Website Redesign", TeamID: teams[0].ID},
		{ID: uuid.New().String()[:8], Name: "API Development", TeamID: teams[1].ID},
		{ID: uuid.New().String()[:8], Name: "Testing Framework", TeamID: teams[3].ID},
	}
	database.DB.Create(&projects)

	tasks := []model.Task{
		{ID: uuid.New().String()[:8], Name: "Design homepage", Description: "Create wireframes and mockups", ProjectID: projects[0].ID},
		{ID: uuid.New().String()[:8], Name: "Implement REST API", Description: "Build CRUD endpoints", ProjectID: projects[1].ID, Finished: true, FinishedBy: "charlie@example.com"},
		{ID: uuid.New().String()[:8], Name: "Write unit tests", Description: "Cover core services", ProjectID: projects[2].ID},
	}
	database.DB.Create(&tasks)

	comments := []model.TaskComment{
		{ID: uuid.New().String()[:8], TaskID: tasks[0].ID, UserID: "alice@example.com", Content: "Looking great!"},
		{ID: uuid.New().String()[:8], TaskID: tasks[1].ID, UserID: "charlie@example.com", Content: "API is ready for review"},
	}
	database.DB.Create(&comments)

	starredTasks := []model.StarredTask{
		{UserID: "alice@example.com", TaskID: tasks[0].ID},
		{UserID: "bob@example.com", TaskID: tasks[1].ID},
	}
	database.DB.Create(&starredTasks)

	projectMembers := []model.ProjectMember{
		{ProjectID: projects[0].ID, Email: "alice@example.com"},
		{ProjectID: projects[0].ID, Email: "bob@example.com"},
		{ProjectID: projects[1].ID, Email: "charlie@example.com"},
		{ProjectID: projects[1].ID, Email: "diana@example.com"},
		{ProjectID: projects[2].ID, Email: "alice@example.com"},
	}
	database.DB.Create(&projectMembers)

	notifications := []model.Notification{
		{ID: uuid.New().String()[:8], Type: "task_assignment", Title: "New Task", Content: "You have been assigned a new task", Link: "/tasks"},
		{ID: uuid.New().String()[:8], Type: "comment", Title: "New Comment", Content: "Someone commented on your task", Link: "/tasks"},
	}
	database.DB.Create(&notifications)

	userNotifications := []model.UserNotification{
		{UserID: "alice@example.com", NotificationID: notifications[0].ID},
		{UserID: "bob@example.com", NotificationID: notifications[1].ID},
	}
	database.DB.Create(&userNotifications)

	activities := []model.ActivityHistory{
		{ID: uuid.New().String()[:8], UserID: "admin@example.com", Description: "Created new project"},
		{ID: uuid.New().String()[:8], UserID: "alice@example.com", Description: "Completed task: Design homepage"},
		{ID: uuid.New().String()[:8], UserID: "charlie@example.com", Description: "Added comment to task"},
	}
	database.DB.Create(&activities)

	chatRooms := []model.ChatRoom{
		{RoomID: "general"},
	}
	database.DB.Create(&chatRooms)

	now := time.Now()
	messages := []model.ChatMessage{
		{RoomID: "general", SenderID: "alice@example.com", Content: "Hey everyone!", CreatedAt: now},
		{RoomID: "general", SenderID: "bob@example.com", Content: "Hi Alice!", CreatedAt: now.Add(time.Minute)},
	}
	database.DB.Create(&messages)

	log.Println("Database seeded successfully!")
}
