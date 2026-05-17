package handler

import (
	"bytes"
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"os"
	"testing"

	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/service"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/testutil"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/utils"
	"gorm.io/gorm"
)

func setupTestEnv() func() {
	original := os.Getenv("JWT_SECRET")
	os.Setenv("JWT_SECRET", "test-secret")
	gin.SetMode(gin.TestMode)
	return func() {
		os.Setenv("JWT_SECRET", original)
	}
}

func TestAuthHandler_LoginValidation(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/login", bytes.NewReader([]byte(`{}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h := &AuthHandler{}
	h.Login(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func TestAuthHandler_LoginMissingPassword(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/login", bytes.NewReader([]byte(`{"email":"test@test.com"}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h := &AuthHandler{}
	h.Login(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func TestAuthHandler_LoginInvalidEmail(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/login", bytes.NewReader([]byte(`{"email":"not-an-email","password":"test123"}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h := &AuthHandler{}
	h.Login(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func TestAuthHandler_VerifyEmailValidation(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/register/verification", bytes.NewReader([]byte(`{}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h := &AuthHandler{}
	h.VerifyEmail(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func TestAuthHandler_PasswordResetValidation(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/password-reset", bytes.NewReader([]byte(`{}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h := &AuthHandler{}
	h.RequestPasswordReset(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func TestAuthHandler_ResetPasswordValidation(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/password-reset/confirmation", bytes.NewReader([]byte(`{}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h := &AuthHandler{}
	h.ResetPassword(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func TestAuthHandler_ResetPasswordShortPassword(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/password-reset/confirmation", bytes.NewReader([]byte(`{"resetPasswordToken":"token","newPassword":"123"}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h := &AuthHandler{}
	h.ResetPassword(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func TestAuthHandler_VerifyResetTokenValidation(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/password-reset/verification", bytes.NewReader([]byte(`{}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h := &AuthHandler{}
	h.VerifyResetToken(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func TestAuthHandler_LogoutValidation(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/logout", bytes.NewReader([]byte(`{}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h := &AuthHandler{}
	h.Logout(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func TestAuthHandler_RegisterMissingFields(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/register", nil)
	c.Request.Header.Set("Content-Type", "multipart/form-data")

	h := &AuthHandler{}
	h.Register(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func TestAuthHandler_RegisterValidation(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	db := setupTestHandlerDB(t)
	authSvc := service.NewAuthService(db, &utils.EmailSender{})
	h := NewAuthHandler(authSvc)

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)

	body := &bytes.Buffer{}
	writer := newTestMultipartWriter(body)
	writer.WriteField("email", "test@test.com")
	writer.WriteField("password", "123")
	writer.WriteField("name", "Test User")
	writer.Close()

	c.Request = httptest.NewRequest(http.MethodPost, "/auth/register", body)
	c.Request.Header.Set("Content-Type", writer.FormDataContentType())

	h.Register(c)

	if w.Code != http.StatusBadRequest {
		t.Errorf("Expected status %d, got %d", http.StatusBadRequest, w.Code)
	}
}

func newTestMultipartWriter(body *bytes.Buffer) *multipartWriter {
	return &multipartWriter{body: body}
}

type multipartWriter struct {
	body *bytes.Buffer
}

func (w *multipartWriter) WriteField(key, value string) error {
	w.body.WriteString("--boundary\r\n")
	w.body.WriteString("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n")
	w.body.WriteString(value + "\r\n")
	return nil
}

func (w *multipartWriter) Close() error {
	w.body.WriteString("--boundary--\r\n")
	return nil
}

func (w *multipartWriter) FormDataContentType() string {
	return "multipart/form-data; boundary=boundary"
}

func setupTestHandlerDB(t *testing.T) *gorm.DB {
	t.Helper()
	return testutil.SetupTestDB(t)
}

func TestAuthHandler_LoginReturnsTokenOnSuccess(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	db := setupTestHandlerDB(t)
	authSvc := service.NewAuthService(db, &utils.EmailSender{})
	h := NewAuthHandler(authSvc)

	hashed, _ := utils.HashPassword("password123")
	db.Exec("INSERT INTO users (email, password, name, role, is_verified) VALUES (?, ?, ?, ?, ?)",
		"verified@test.com", hashed, "Verified User", "user", true)

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/login", bytes.NewReader([]byte(`{"email":"verified@test.com","password":"password123"}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h.Login(c)

	if w.Code != http.StatusOK {
		t.Errorf("Expected status %d, got %d", http.StatusOK, w.Code)
	}

	var resp map[string]interface{}
	json.Unmarshal(w.Body.Bytes(), &resp)

	if resp["token"] == nil {
		t.Error("Expected token in response")
	}
}

func TestAuthHandler_LoginFailsWithWrongPassword(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	db := setupTestHandlerDB(t)
	authSvc := service.NewAuthService(db, &utils.EmailSender{})
	h := NewAuthHandler(authSvc)

	hashed, _ := utils.HashPassword("correctpassword")
	db.Exec("INSERT INTO users (email, password, name, role, is_verified) VALUES (?, ?, ?, ?, ?)",
		"wrongpass@test.com", hashed, "Wrong Pass User", "user", true)

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/login", bytes.NewReader([]byte(`{"email":"wrongpass@test.com","password":"wrongpassword"}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h.Login(c)

	if w.Code != http.StatusUnauthorized {
		t.Errorf("Expected status %d, got %d", http.StatusUnauthorized, w.Code)
	}
}

func TestAuthHandler_LoginFailsWithUnverifiedEmail(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	db := setupTestHandlerDB(t)
	authSvc := service.NewAuthService(db, &utils.EmailSender{})
	h := NewAuthHandler(authSvc)

	hashed, _ := utils.HashPassword("password123")
	db.Exec("INSERT INTO users (email, password, name, role, is_verified) VALUES (?, ?, ?, ?, ?)",
		"unverified@test.com", hashed, "Unverified User", "user", false)

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/login", bytes.NewReader([]byte(`{"email":"unverified@test.com","password":"password123"}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h.Login(c)

	if w.Code != http.StatusUnauthorized {
		t.Errorf("Expected status %d, got %d", http.StatusUnauthorized, w.Code)
	}
}

func TestAuthHandler_LoginFailsWithNonExistentEmail(t *testing.T) {
	teardown := setupTestEnv()
	defer teardown()

	db := setupTestHandlerDB(t)
	authSvc := service.NewAuthService(db, &utils.EmailSender{})
	h := NewAuthHandler(authSvc)

	w := httptest.NewRecorder()
	c, _ := gin.CreateTestContext(w)
	c.Request = httptest.NewRequest(http.MethodPost, "/auth/login", bytes.NewReader([]byte(`{"email":"nonexistent@test.com","password":"password123"}`)))
	c.Request.Header.Set("Content-Type", "application/json")

	h.Login(c)

	if w.Code != http.StatusUnauthorized {
		t.Errorf("Expected status %d, got %d", http.StatusUnauthorized, w.Code)
	}
}
