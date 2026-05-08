# Golang Backend

A Go/Gin REST API (currently a starter template).

## Prerequisites

- **Go** 1.15+
- **Database**: PostgreSQL

## Quick Start (CLI - VS Code Integrated Terminal)

```bash
cd golang

# Install dependencies
go mod tidy

# Create environment file
copy .env.example .env
# Edit .env with your database credentials

# Run the application
go run main.go
```

The API will be available at `http://localhost:8000`

## Available Go Commands

| Command | Description |
|---------|-------------|
| `go run main.go` | Start development server |
| `go build` | Build the binary |
| `go test` | Run tests |
| `go mod tidy` | Clean up dependencies |

## Installing Packages

```bash
# Add a package
go get github.com/package/name

# Example
go get github.com/gin-gonic/gin
```

## Environment Variables

Create a `.env` file in the root:

```env
PORT=8000
DB_HOST=localhost
DB_PORT=5432
DB_USER=postgres
DB_PASSWORD=yourpassword
DB_NAME=mydb
```

## Project Structure

```
golang/
├── main.go           # Entry point
├── config/           # Database config
├── models/           # Data models
├── handlers/         # HTTP handlers
├── go.mod
└── go.sum
```

## Current Status

⚠️ This is a basic starter template with only:
- Basic User CRUD operations
- GORM database setup

The following features are NOT yet implemented:
- Authentication (JWT)
- Teams, Projects, Tasks
- WebSocket/Chat
- Profile management
- Activity logging