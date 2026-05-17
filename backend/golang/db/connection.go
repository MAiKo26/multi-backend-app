package db

import (
	"fmt"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

type DB struct {
	*gorm.DB
}

type ConnectionConfig struct {
	Host     string
	Port     string
	User     string
	Password string
	DBName   string
}

func NewConnection(cfg ConnectionConfig) (*DB, error) {
	dsn := fmt.Sprintf(
		"host=%s user=%s password=%s dbname=%s port=%s sslmode=disable",
		cfg.Host, cfg.User, cfg.Password, cfg.DBName, cfg.Port,
	)

	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		return nil, fmt.Errorf("failed to connect to database: %w", err)
	}

	return &DB{DB: db}, nil
}

func (d *DB) AutoMigrate(models ...interface{}) error {
	return d.DB.AutoMigrate(models...)
}