package main

import (
	"github.com/maiko26/multi-backend-app/backend/golang/models"
	"github.com/maiko26/multi-backend-app/backend/golang/routes"
)

// Entrypoint for app.
func main() {
	// Load the routes
	r := routes.SetupRouter()

	// Initialize database
	models.SetupDatabase()

	// Start the HTTP API
	r.Run()
}
