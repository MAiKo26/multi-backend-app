package main

import (
	"log"
	"os"
	"time"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"github.com/joho/godotenv"
	"github.com/maiko26/multi-backend-app/backend/golang/db"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/config"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/handler"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/middleware"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/model"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/repository"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/service"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/utils"
	"github.com/maiko26/multi-backend-app/backend/golang/routers"
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
)

// @title Multi-Backend App API
// @version 1.0
// @description Go backend API matching Express backend functionality. Project management platform with authentication, teams, projects, tasks, and real-time chat.
// @host localhost:3000
// @BasePath /
// @securityDefinitions.apikey BearerAuth
// @in header
// @name Authorization
func main() {
	if err := godotenv.Load(); err != nil {
		log.Println("No .env file found, using system env vars")
	}

	cfg := config.New()

	database, err := db.NewConnection(db.ConnectionConfig{
		Host:     cfg.DB.Host,
		Port:     cfg.DB.Port,
		User:     cfg.DB.User,
		Password: cfg.DB.Password,
		DBName:   cfg.DB.Name,
	})
	if err != nil {
		log.Fatalf("Failed to connect to database: %v", err)
	}

	if err := database.AutoMigrate(
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
	); err != nil {
		log.Fatalf("Failed to run migrations: %v", err)
	}

	db.Seed(database)

	emailSender := utils.NewEmailSender(cfg.SMTP.Host, cfg.SMTP.Port, cfg.SMTP.User, cfg.SMTP.Password, cfg.SMTP.From)

	userRepo := repository.NewUserRepository(database.DB)
	teamRepo := repository.NewTeamRepository(database.DB)
	projectRepo := repository.NewProjectRepository(database.DB)
	taskRepo := repository.NewTaskRepository(database.DB)
	activityRepo := repository.NewActivityRepository(database.DB)

	userService := service.NewUserService(userRepo, database.DB)
	userHandler := handler.NewUserHandler(userService)

	authService := service.NewAuthService(database.DB, emailSender)
	authHandler := handler.NewAuthHandler(authService)

	teamService := service.NewTeamService(teamRepo, userRepo, database.DB)
	teamHandler := handler.NewTeamHandler(teamService)

	projectService := service.NewProjectService(projectRepo, userRepo, database.DB)
	projectHandler := handler.NewProjectHandler(projectService)

	taskService := service.NewTaskService(taskRepo, database.DB)
	taskHandler := handler.NewTaskHandler(taskService)

	profileHandler := handler.NewProfileHandler(userService)

	activityService := service.NewActivityService(activityRepo)
	activityHandler := handler.NewActivityHandler(activityService)

	chatService := service.NewChatService(database.DB)
	chatHandler := handler.NewChatHandler(chatService)

	r := gin.Default()

	r.Use(cors.New(cors.Config{
		AllowOrigins:     []string{"http://localhost:5173"},
		AllowMethods:     []string{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"},
		AllowHeaders:     []string{"Origin", "Content-Type", "Accept", "Authorization"},
		ExposeHeaders:    []string{"Content-Length"},
		AllowCredentials: true,
		MaxAge:           12 * time.Hour,
	}))

	r.Static("/uploads", "./uploads")

	r.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	r.Use(middleware.RateLimitMiddleware(100, 10))

	routers.Setup(r, userHandler, authHandler, teamHandler, projectHandler, taskHandler, profileHandler, activityHandler, chatHandler)

	port := cfg.Server.Port
	if port == "" {
		port = "3000"
	}

	log.Printf("Server starting on port %s", port)
	if err := r.Run(":" + port); err != nil {
		log.Fatalf("Failed to start server: %v", err)
		os.Exit(1)
	}
}
