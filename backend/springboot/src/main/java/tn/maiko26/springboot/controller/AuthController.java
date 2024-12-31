package tn.maiko26.springboot.controller;

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

class AuthRequestData {
    private String email;
    private String password;
    private String name;

    public AuthRequestData() {
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AuthRequestData)) return false;
        final AuthRequestData other = (AuthRequestData) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AuthRequestData;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "AuthRequestData(email=" + this.getEmail() + ", password=" + this.getPassword() + ", name=" + this.getName() + ")";
    }
}

class PasswordResetRequestDTO {
    private String newPassword;
    private String resetPasswordToken;

    public PasswordResetRequestDTO() {
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public String getResetPasswordToken() {
        return this.resetPasswordToken;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PasswordResetRequestDTO)) return false;
        final PasswordResetRequestDTO other = (PasswordResetRequestDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$newPassword = this.getNewPassword();
        final Object other$newPassword = other.getNewPassword();
        if (this$newPassword == null ? other$newPassword != null : !this$newPassword.equals(other$newPassword))
            return false;
        final Object this$resetPasswordToken = this.getResetPasswordToken();
        final Object other$resetPasswordToken = other.getResetPasswordToken();
        if (this$resetPasswordToken == null ? other$resetPasswordToken != null : !this$resetPasswordToken.equals(other$resetPasswordToken))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PasswordResetRequestDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $newPassword = this.getNewPassword();
        result = result * PRIME + ($newPassword == null ? 43 : $newPassword.hashCode());
        final Object $resetPasswordToken = this.getResetPasswordToken();
        result = result * PRIME + ($resetPasswordToken == null ? 43 : $resetPasswordToken.hashCode());
        return result;
    }

    public String toString() {
        return "PasswordResetRequestDTO(newPassword=" + this.getNewPassword() + ", resetPasswordToken=" + this.getResetPasswordToken() + ")";
    }
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
