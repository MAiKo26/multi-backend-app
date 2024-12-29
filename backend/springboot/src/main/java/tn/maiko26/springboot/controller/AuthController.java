package tn.maiko26.springboot.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.maiko26.springboot.service.AuthenticationService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    // Logging in

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestData request) {
        try {
            String token = authenticationService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok().body(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> payload) {
        String sessionId = payload.get("verificationToken");

        authenticationService.logout(sessionId);
        return ResponseEntity.ok().body("Logged out successfully");
    }

    // Registering

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestData request) {

        authenticationService.register(request.getEmail(), request.getPassword(), request.getName());
        return ResponseEntity.ok().body(Map.of("message", "Email Sent Successfully"));

    }

    @PostMapping("/register/verification")
    public ResponseEntity<?> registerVerification(@RequestBody Map<String, String> payload) {

        String verificationToken = payload.get("verificationToken");

        authenticationService.registerVerification(verificationToken);
        return ResponseEntity.ok().body(Map.of("message", "Verification successful"));

    }

    // Password Reset

    @PostMapping("/password-reset")
    public ResponseEntity<?> passwordReset(@RequestBody Map<String, String> payload) {

        String email = payload.get("email");

        authenticationService.passwordReset(email);
        return ResponseEntity.ok().body(Map.of("message", "Password reset link sent to email"));


    }

    @PostMapping("/password-reset/verification")
    public ResponseEntity<?> passwordResetVerification(@RequestBody Map<String, String> payload) {

        String resetPasswordToken = payload.get("resetPasswordToken");

        authenticationService.passwordResetVerification(resetPasswordToken);
        return ResponseEntity.ok().body(Map.of("message", "Valid token"));

    }

    @PostMapping("/password-reset/confirmation")
    public ResponseEntity<?> passwordResetConfirmation(@RequestBody PasswordResetRequestDTO request) {

        authenticationService.passwordResetConfirmation(request.getResetPasswordToken(), request.getNewPassword());
        return ResponseEntity.ok().body(Map.of("message", "Password reset successful"));


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
