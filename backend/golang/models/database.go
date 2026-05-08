package models

import (
	"fmt"

	"github.com/maiko26/multi-backend-app/backend/golang/helper"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

var DB *gorm.DB

func SetupDatabase() {
	u := helper.GetEnv("DATABASE_USER", "golang")
	p := helper.GetEnv("DATABASE_PASSWORD", "golang") // fixed spelling
	h := helper.GetEnv("DATABASE_HOST", "localhost")  // NO port here
	pt := helper.GetEnv("DATABASE_PORT", "5432")      // separate
	n := helper.GetEnv("DATABASE_NAME", "go_test")

	dsn := fmt.Sprintf(
		"host=%s user=%s password=%s dbname=%s port=%s sslmode=disable",
		h, u, p, n, pt,
	)

	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		panic(fmt.Sprintf("Could not open database: %v", err))
	}

	// only migrate after we know db is alive
	if err := db.AutoMigrate(&User{}); err != nil {
		panic(fmt.Sprintf("AutoMigrate failed: %v", err))
	}

	DB = db
}
