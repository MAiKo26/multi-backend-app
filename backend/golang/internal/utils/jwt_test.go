package utils

import (
	"os"
	"testing"
	"time"

	"github.com/golang-jwt/jwt/v5"
)

func setupTestJWT() func() {
	original := os.Getenv("JWT_SECRET")
	os.Setenv("JWT_SECRET", "test-secret-key-for-testing")
	return func() {
		os.Setenv("JWT_SECRET", original)
	}
}

func TestGenerateToken(t *testing.T) {
	teardown := setupTestJWT()
	defer teardown()

	token, err := GenerateToken("test@example.com", "user")
	if err != nil {
		t.Fatalf("GenerateToken() error = %v", err)
	}

	if token == "" {
		t.Fatal("GenerateToken() returned empty token")
	}

	claims, err := ValidateToken(token)
	if err != nil {
		t.Fatalf("ValidateToken() error = %v", err)
	}

	if claims.Email != "test@example.com" {
		t.Errorf("Expected email 'test@example.com', got '%s'", claims.Email)
	}

	if claims.Role != "user" {
		t.Errorf("Expected role 'user', got '%s'", claims.Role)
	}
}

func TestGenerateTokenAdmin(t *testing.T) {
	teardown := setupTestJWT()
	defer teardown()

	token, err := GenerateToken("admin@example.com", "admin")
	if err != nil {
		t.Fatalf("GenerateToken() error = %v", err)
	}

	claims, err := ValidateToken(token)
	if err != nil {
		t.Fatalf("ValidateToken() error = %v", err)
	}

	if claims.Role != "admin" {
		t.Errorf("Expected role 'admin', got '%s'", claims.Role)
	}
}

func TestValidateTokenInvalid(t *testing.T) {
	teardown := setupTestJWT()
	defer teardown()

	_, err := ValidateToken("invalid-token-string")
	if err == nil {
		t.Fatal("ValidateToken() expected error for invalid token, got nil")
	}
}

func TestValidateTokenEmpty(t *testing.T) {
	teardown := setupTestJWT()
	defer teardown()

	_, err := ValidateToken("")
	if err == nil {
		t.Fatal("ValidateToken() expected error for empty token, got nil")
	}
}

func TestValidateTokenTampered(t *testing.T) {
	teardown := setupTestJWT()
	defer teardown()

	validToken, _ := GenerateToken("test@example.com", "user")

	tampered := validToken + "tampered"
	_, err := ValidateToken(tampered)
	if err == nil {
		t.Fatal("ValidateToken() expected error for tampered token, got nil")
	}
}

func TestValidateTokenWrongSecret(t *testing.T) {
	teardown := setupTestJWT()
	defer teardown()

	token, _ := GenerateToken("test@example.com", "user")

	os.Setenv("JWT_SECRET", "different-secret")
	defer os.Setenv("JWT_SECRET", "test-secret-key-for-testing")

	_, err := ValidateToken(token)
	if err == nil {
		t.Fatal("ValidateToken() expected error for wrong secret, got nil")
	}
}

func TestTokenExpiration(t *testing.T) {
	teardown := setupTestJWT()
	defer teardown()

	token, err := GenerateToken("test@example.com", "user")
	if err != nil {
		t.Fatalf("GenerateToken() error = %v", err)
	}

	parser := jwt.NewParser()
	claims := &Claims{}
	_, _, err = parser.ParseUnverified(token, claims)
	if err != nil {
		t.Fatalf("ParseUnverified() error = %v", err)
	}

	expectedExpiry := time.Now().Add(1 * time.Hour)
	diff := claims.ExpiresAt.Time.Sub(expectedExpiry)
	if diff < -time.Second || diff > time.Second {
		t.Errorf("Token expiry should be ~1 hour, got difference: %v", diff)
	}
}

func TestHashPassword(t *testing.T) {
	password := "securepassword123"

	hashed, err := HashPassword(password)
	if err != nil {
		t.Fatalf("HashPassword() error = %v", err)
	}

	if hashed == password {
		t.Fatal("HashPassword() returned same as input")
	}

	if len(hashed) < 20 {
		t.Fatal("HashPassword() returned suspiciously short hash")
	}
}

func TestHashPasswordDifferentEachTime(t *testing.T) {
	password := "samepassword"

	hash1, err := HashPassword(password)
	if err != nil {
		t.Fatalf("HashPassword() error = %v", err)
	}

	hash2, err := HashPassword(password)
	if err != nil {
		t.Fatalf("HashPassword() error = %v", err)
	}

	if hash1 == hash2 {
		t.Fatal("HashPassword() should produce different hashes for same password (salt)")
	}
}

func TestComparePasswordValid(t *testing.T) {
	password := "testpassword123"

	hashed, err := HashPassword(password)
	if err != nil {
		t.Fatalf("HashPassword() error = %v", err)
	}

	err = ComparePassword(hashed, password)
	if err != nil {
		t.Fatalf("ComparePassword() error = %v", err)
	}
}

func TestComparePasswordInvalid(t *testing.T) {
	password := "correctpassword"
	wrongPassword := "wrongpassword"

	hashed, err := HashPassword(password)
	if err != nil {
		t.Fatalf("HashPassword() error = %v", err)
	}

	err = ComparePassword(hashed, wrongPassword)
	if err == nil {
		t.Fatal("ComparePassword() expected error for wrong password, got nil")
	}
}

func TestComparePasswordEmptyHash(t *testing.T) {
	err := ComparePassword("", "password")
	if err == nil {
		t.Fatal("ComparePassword() expected error for empty hash, got nil")
	}
}

func TestTokenRoundTrip(t *testing.T) {
	teardown := setupTestJWT()
	defer teardown()

	testCases := []struct {
		email string
		role  string
	}{
		{"user@example.com", "user"},
		{"admin@example.com", "admin"},
		{"test+special@example.com", "user"},
	}

	for _, tc := range testCases {
		token, err := GenerateToken(tc.email, tc.role)
		if err != nil {
			t.Fatalf("GenerateToken() error = %v", err)
		}

		claims, err := ValidateToken(token)
		if err != nil {
			t.Fatalf("ValidateToken() error = %v", err)
		}

		if claims.Email != tc.email {
			t.Errorf("Expected email '%s', got '%s'", tc.email, claims.Email)
		}

		if claims.Role != tc.role {
			t.Errorf("Expected role '%s', got '%s'", tc.role, claims.Role)
		}
	}
}
