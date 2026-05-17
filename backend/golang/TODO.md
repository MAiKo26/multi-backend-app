# 📋 Detailed Migration Plan: Express → Go Backend

## Overview

Your Express backend is a full-featured project management app with:

- **14 database tables** (users, teams, projects, tasks, chat, etc.)
- **JWT authentication** with session management
- **WebSocket** for real-time chat
- **File uploads, email, rate limiting**

Your Go backend already has the foundation (Gin + GORM + basic user CRUD), but needs ~80% more implementation.

---

## 🗂️ Recommended Architecture (Go)

```
backend/golang/
├── cmd/
│   └── server/
│       └── main.go              # Entry point
├── internal/
│   ├── config/                   # Configuration
│   ├── middleware/              # Auth, CORS, rate limit, error handling
│   ├── model/                   # GORM models (database schema)
│   ├── repository/              # Database operations
│   ├── service/                 # Business logic
│   ├── handler/                 # HTTP handlers
│   └── utils/                   # Helpers (email, file upload, etc.)
├── pkg/
│   └── response/                # Standardized responses
├── routers/
│   ├── router.go               # Main router
│   └── v1/                     # Versioned API routes
└── migrations/                  # Database migrations
```

---

## 📦 Recommended Go Libraries

| Purpose              | Library                                | Why                                        |
| -------------------- | -------------------------------------- | ------------------------------------------ |
| **ORM**              | `gorm.io/gorm`                         | Already in use, mature, PostgreSQL support |
| **Database**         | `gorm.io/driver/postgres`              | PostgreSQL driver for GORM                 |
| **Web Framework**    | `gin-gonic/gin`                        | Already in use, fast, middleware support   |
| **JWT**              | `golang-jwt/jwt/v5`                    | Latest JWT Go library                      |
| **Password Hashing** | `golang.org/x/crypto/bcrypt`           | Go's standard bcrypt                       |
| **WebSocket**        | `github.com/gorilla/websocket`         | Industry standard                          |
| **Validation**       | `go-playground/validator/v10`          | Already in go.mod                          |
| **Environment**      | `joho/godotenv`                        | Already in go.mod                          |
| **Email**            | `gopkg.in/gomail.v2`                   | Simple SMTP                                |
| **Rate Limiting**    | `github.com/gin-contrib/cors` + custom | CORS already, build custom rate limiter    |
| **UUID**             | `github.com/google/uuid`               | Generate unique IDs                        |
| **Logging**          | `log/slog` (built-in)                  | Go 1.21+ structured logging                |

---

## 📅 Phased Implementation Plan

---

### **Phase 1: Foundation & Database Schema**

_(Estimated: 1-2 weeks)_

**Goal:** Recreate all database models and setup migrations

#### Step 1.1: Install Additional Dependencies

```bash
cd backend/golang
go get gorm.io/gorm
go get gorm.io/driver/postgres
go get github.com/golang-jwt/jwt/v5
go get golang.org/x/crypto/bcrypt
go get github.com/google/uuid
go get gopkg.in/gomail.v2
go get github.com/gorilla/websocket
go get github.com/gin-contrib/cors
```

#### Step 1.2: Create Database Models

Create all GORM models matching Express schema:

```go
// internal/model/user.go - EXPAND existing model
type User struct {
    ID            uint      `gorm:"primaryKey" json:"id"`
    CreatedAt     time.Time `json:"created_at"`
    UpdatedAt     time.Time `json:"updated_at"`
    Email         string    `gorm:"uniqueIndex;not null" json:"email"`
    Password      string    `gorm:"not null" json:"-"`
    Name          string    `json:"name"`
    Avatar        string    `json:"avatar"`
    Role          string    `gorm:"default:'user'" json:"role"` // "admin" or "user"
    IsVerified    bool      `gorm:"default:false" json:"is_verified"`
    VerificationToken string `json:"-"`
    LockedUntil   *time.Time `json:"-"`
    FailedAttempts int      `gorm:"default:0" json:"-"`
    Subscription  string    `gorm:"default:'free'" json:"subscription"`
    Points        int       `gorm:"default:0" json:"points"`
}

// internal/model/team.go
type Team struct {
    ID          uint      `gorm:"primaryKey" json:"id"`
    CreatedAt   time.Time `json:"created_at"`
    UpdatedAt   time.Time `json:"updated_at"`
    Name        string    `gorm:"not null" json:"name"`
    Description string    `json:"description"`
    OwnerID     uint      `gorm:"not null" json:"owner_id"`
    Members     []TeamMember `gorm:"foreignKey:TeamID"`
    Projects    []Project   `gorm:"foreignKey:TeamID"`
}

// internal/model/team_member.go
type TeamMember struct {
    ID        uint      `gorm:"primaryKey" json:"id"`
    TeamID    uint      `gorm:"not null" json:"team_id"`
    UserID    uint      `gorm:"not null" json:"user_id"`
    Role      string    `gorm:"default:'member'" json:"role"` // "admin", "member"
    JoinedAt  time.Time `json:"joined_at"`
}

// internal/model/project.go
type Project struct {
    ID          uint      `gorm:"primaryKey" json:"id"`
    CreatedAt   time.Time `json:"created_at"`
    UpdatedAt   time.Time `json:"updated_at"`
    Name        string    `gorm:"not null" json:"name"`
    Description string    `json:"description"`
    TeamID      uint      `gorm:"not null" json:"team_id"`
    Members     []ProjectMember `gorm:"foreignKey:ProjectID"`
    Tasks       []Task    `gorm:"foreignKey:ProjectID"`
}

// internal/model/task.go
type Task struct {
    ID          uint      `gorm:"primaryKey" json:"id"`
    CreatedAt   time.Time `json:"created_at"`
    UpdatedAt   time.Time `json:"updated_at"`
    Title       string    `gorm:"not null" json:"title"`
    Description string    `json:"description"`
    Status      string    `gorm:"default:'todo'" json:"status"` // "todo", "in_progress", "done"
    Priority    string    `gorm:"default:'medium'" json:"priority"`
    DueDate     *time.Time `json:"due_date"`
    ProjectID   uint      `gorm:"not null" json:"project_id"`
    AssigneeID  *uint     `json:"assignee_id"`
    Comments    []TaskComment `gorm:"foreignKey:TaskID"`
    StarredBy   []StarredTask  `gorm:"foreignKey:TaskID"`
}

// ... Continue with other models
```

**Models to create:**

- [ ] User (expand existing)
- [ ] UserSettings
- [ ] Session (for JWT token storage)
- [ ] Team
- [ ] TeamMember
- [ ] Project
- [ ] ProjectMember
- [ ] Task
- [ ] TaskComment
- [ ] StarredTask
- [ ] Notification
- [ ] UserNotification
- [ ] ActivityHistory
- [ ] ChatRoom
- [ ] ChatMessage

#### Step 1.3: Setup Auto-Migration

Update `db/connection.go` to auto-create all tables:

```go
// db/connection.go
func InitDB() *gorm.DB {
    // ... existing connection code ...

    // Auto migrate all models
    db.AutoMigrate(
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

    return db
}
```

#### Step 1.4: Create Seeder

Create `db/seed.go` to populate initial data (admin user, default teams).

---

### **Phase 2: Authentication System**

_(Estimated: 1 week)_

**Goal:** Implement JWT auth matching Express behavior

#### Step 2.1: Create JWT Utility

```go
// internal/utils/jwt.go
package utils

import (
    "time"
    "github.com/golang-jwt/jwt/v5"
)

type Claims struct {
    UserID uint   `json:"user_id"`
    Email  string `json:"email"`
    Role   string `json:"role"`
    jwt.RegisteredClaims
}

func GenerateToken(userID uint, email, role string) (string, error) {
    claims := &Claims{
        UserID: userID,
        Email:  email,
        Role:   role,
        RegisteredClaims: jwt.RegisteredClaims{
            ExpiresAt: jwt.NewNumericDate(time.Now().Add(24 * time.Hour)),
            IssuedAt:  jwt.NewNumericDate(time.Now()),
        },
    }
    token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
    return token.SignedString([]byte(os.Getenv("JWT_SECRET")))
}

func ValidateToken(tokenString string) (*Claims, error) {
    // ... validation logic
}
```

#### Step 2.2: Create Auth Middleware

```go
// internal/middleware/auth.go
func AuthMiddleware() gin.HandlerFunc {
    return func(c *gin.Context) {
        // Extract token from header
        // Validate token
        // Set user info in context
        // Continue to next handler
    }
}
```

#### Step 2.3: Create Admin Middleware

```go
// internal/middleware/admin.go
func AdminMiddleware() gin.HandlerFunc {
    return func(c *gin.Context) {
        // Check if user role == "admin"
        // Return 403 if not admin
    }
}
```

#### Step 2.4: Create Auth Handlers

```go
// internal/handler/auth.go
func Register(c *gin.Context)
func Login(c *gin.Context)
func Logout(c *gin.Context)
func VerifyEmail(c *gin.Context)
func RequestPasswordReset(c *gin.Context)
func ResetPassword(c *gin.Context)
```

#### Step 2.5: Add Auth Routes

```go
// routers/v1/auth.go
func SetupAuthRoutes(router *gin.Engine) {
    auth := router.Group("/api/v1/auth")
    {
        auth.POST("/register", handler.Register)
        auth.POST("/login", handler.Login)
        auth.POST("/logout", middleware.AuthMiddleware(), handler.Logout)
        auth.POST("/password-reset", handler.RequestPasswordReset)
        // ... etc
    }
}
```

---

### **Phase 3: Core API Features**

_(Estimated: 2-3 weeks)_

**Goal:** Implement all remaining CRUD operations

#### Step 3.1: Users API

- GET /users - List all users
- GET /users/byteam/:teamId
- GET /users/online
- PATCH /users/:id

#### Step 3.2: Teams API

- GET /teams - List teams (admin)
- POST /teams - Create team (admin)
- PUT /teams/:id - Update team (admin)
- DELETE /teams/:id - Delete team (admin)
- POST /teams/members - Add member (admin)
- GET /teams/byuser/:email

#### Step 3.3: Projects API

- GET /projects/:teamId
- POST /projects - Create project
- POST /projects/members - Add member
- DELETE /projects/:id

#### Step 3.4: Tasks API

- GET /tasks - List all tasks
- GET /tasks/project/:projectId
- POST /tasks - Create task
- PUT /tasks/:id
- DELETE /tasks/:id
- POST /tasks/comments/:taskId
- POST /tasks/star/:taskId

#### Step 3.5: Profile API

- GET /profile - Get current user profile
- PUT /profile - Update profile
- PUT /profile/password - Change password
- PUT /profile/settings - Update notifications

#### Step 3.6: Activity API

- GET /activity - List all activities
- GET /activity/user/:email

---

### **Phase 4: Real-time Features**

_(Estimated: 1 week)_

**Goal:** WebSocket for chat

#### Step 4.1: Setup WebSocket Handler

```go
// internal/handler/websocket.go
func HandleChat(c *gin.Context) {
    // Upgrade to WebSocket
    // Handle messages
    // Broadcast to room
}
```

#### Step 4.2: Chat History Endpoint

- GET /chat/history/:roomId

#### Step 4.3: Chat WebSocket Routes

- WS /chat - General chat
- WS /chat/:roomId - Private chat

---

### **Phase 5: Utilities & Polish**

_(Estimated: 1 week)_

**Goal:** Match Express feature-for-feature

#### Step 5.1: Email Service

```go
// internal/utils/email.go
func SendEmail(to, subject, body string) error
```

#### Step 5.2: File Upload

```go
// internal/utils/upload.go
func HandleFileUpload(c *gin.Context) // Using multipart/form-data
```

#### Step 5.3: Rate Limiting

```go
// internal/middleware/rate_limit.go
// Implement token bucket or sliding window
```

#### Step 5.4: CORS Middleware

```go
// internal/middleware/cors.go
config := cors.DefaultConfig()
config.AllowOrigins = []string{"http://localhost:5173"}
router.Use(cors.New(config))
```

#### Step 5.5: Error Handling Middleware

```go
// internal/middleware/error.go
func ErrorHandler() gin.HandlerFunc
```

#### Step 5.6: Activity Logging

```go
// internal/utils/activity_logger.go
func LogActivity(userID uint, action, description string)
```

---

### **Phase 6: Testing & Deployment**

_(Estimated: 1 week)_

**Goal:** Ensure stability

#### Step 6.1: Write Unit Tests

- Test handlers
- Test services
- Test utils (JWT, password hashing)

#### Step 6.2: API Documentation

- Add Swagger (using `swaggo/gin-swagger`)

#### Step 6.3: Build & Run

```bash
go build -o server.exe ./cmd/server
./server.exe
```

---

## 📊 Implementation Checklist

```
Phase 1: Foundation ✅
├── [x] Install dependencies
├── [x] Create all 15 models
├── [x] Setup auto-migration
└── [x] Create seeder

Phase 2: Authentication ✅
├── [x] JWT utils
├── [x] Auth middleware
├── [x] Admin middleware
├── [x] Auth handlers (register, login, logout, password reset)
├── [x] Auth routes
├── [x] Email service
└── [x] Activity logging

Phase 3: Core API ✅
├── [x] Users endpoints (GET all, GET by team, GET online, PATCH, DELETE)
├── [x] Teams endpoints (CRUD, members, by user, admin-only)
├── [x] Projects endpoints (by team, create, members, delete)
├── [x] Tasks endpoints (list, by project, CRUD, comments, star)
├── [x] Profile endpoints (get, update, password, settings, by session)
└── [x] Activity endpoints (list, by user)

Phase 4: Real-time ✅
├── [x] WebSocket handler (general + private rooms)
├── [x] Chat history endpoint
└── [x] Chat WebSocket routes

Phase 5: Utilities ✅
├── [x] File upload utility
├── [x] Rate limiting (token bucket)
└── [x] CORS (configured for localhost:5173)

Phase 6: Testing
├── [ ] Unit tests
├── [ ] API docs
└── [ ] Build verification
```

---

## 💡 Pro Tips

1. **Start with Phase 1** - Get all models right first, it makes everything else easier
2. **Use GORM Associations** - Make use of `gorm:"foreignKey"`, `hasMany`, `belongsTo` for relationships
3. **Match Express Validation** - Use `go-playground/validator` with tags like `required,email,min=6`
4. **Reuse the Express Services** - Your Express service logic is great reference, just translate to Go
5. **Environment Variables** - Keep all config in `.env`, don't hardcode

---

## 📚 Resources

- [GORM Documentation](https://gorm.io/docs/)
- [Gin Documentation](https://gin-gonic.com/docs/)
- [JWT Go Library](https://github.com/golang-jwt/jwt)
- [Gorilla WebSocket](https://github.com/gorilla/websocket)

---

Want me to help implement any specific phase? I can start with Phase 1 (models and database setup) if you'd like!

---
