package config

import "os"

type Config struct {
	Server ServerConfig
	DB     DBConfig
	JWT    JWTConfig
	SMTP   SMTPConfig
}

type ServerConfig struct {
	Port string
}

type DBConfig struct {
	Host     string
	Port     string
	User     string
	Password string
	Name     string
}

type JWTConfig struct {
	Secret string
}

type SMTPConfig struct {
	Host     string
	Port     string
	User     string
	Password string
	From     string
}

func New() *Config {
	return &Config{
		Server: ServerConfig{
			Port: getEnv("PORT", "3000"),
		},
		DB: DBConfig{
			Host:     getEnv("DB_HOST", "localhost"),
			Port:     getEnv("DB_PORT", "5432"),
			User:     getEnv("DB_USER", "golang"),
			Password: getEnv("DB_PASSWORD", "golang"),
			Name:     getEnv("DB_NAME", "go_test"),
		},
		JWT: JWTConfig{
			Secret: getEnv("JWT_SECRET", "supersecretkey"),
		},
		SMTP: SMTPConfig{
			Host:     getEnv("SMTP_HOST", "smtp.ethereal.email"),
			Port:     getEnv("SMTP_PORT", "587"),
			User:     getEnv("SMTP_USER", "norma.stiedemann@ethereal.email"),
			Password: getEnv("SMTP_PASS", ""),
			From:     getEnv("SMTP_FROM", "norma.stiedemann@ethereal.email"),
		},
	}
}

func getEnv(key, fallback string) string {
	if value, exists := os.LookupEnv(key); exists {
		return value
	}
	return fallback
}
