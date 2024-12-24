package tn.maiko26.springboot.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.services.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Logging in

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestData request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok().body(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> payload) {
        String sessionId = payload.get("verificationToken");

        authService.logout(sessionId);
        return ResponseEntity.ok().body("Logged out successfully");
    }

    // Registering

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestData request) {
        try {
            authService.register(request.getEmail(), request.getPassword(),request.getName());
            return ResponseEntity.ok().body(Map.of("message", "Email Sent Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/register/verification")
    public ResponseEntity<?> registerVerification(@RequestBody Map<String, String> payload) {
        try {
            String verificationToken = payload.get("verificationToken");

            authService.registerVerification(verificationToken);
            return ResponseEntity.ok().body(Map.of("message", "Verification successful"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }
    }

    // Password Reset

    @PostMapping("/password-reset")
    public ResponseEntity<?> passwordReset(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");

            authService.passwordReset(email);
            return ResponseEntity.ok().body(Map.of("message", "Password reset link sent to email"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }

    }

    @PostMapping("/password-reset/verification")
    public ResponseEntity<?> passwordResetVerification(@RequestBody Map<String, String> payload) {
        try {
            String resetPasswordToken = payload.get("resetPasswordToken");

            authService.passwordResetVerification(resetPasswordToken);
            return ResponseEntity.ok().body(Map.of("message", "Valid token"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }

    }

    @PostMapping("/password-reset/confirmation")
    public ResponseEntity<?> passwordResetConfirmation(@RequestBody PasswordResetRequestDTO request) {
        try {
            authService.passwordResetConfirmation(request.getResetPasswordToken(), request.getNewPassword());
            return ResponseEntity.ok().body(Map.of("message", "Password reset successful"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }

    }


    @ExceptionHandler
    public ResponseEntity<?> conflict() {
        return ResponseEntity.status(500).body("Internal Server Error");
    }
}

@Data
class AuthRequestData {
    private String email;
    private String password;
    private String name;
}

@Data
class PasswordResetRequestDTO {
    private String newPassword;
    private String resetPasswordToken;
}

class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
