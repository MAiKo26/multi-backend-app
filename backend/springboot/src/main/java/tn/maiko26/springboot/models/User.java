package tn.maiko26.springboot.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "email")
    private String email;

    // Auth
    @Column(name = "password")
    private String password;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "reset_password_expiry")
    private Date resetPasswordExpiry;

    @Column(name = "failed_login_attempts", columnDefinition = "integer default 0")
    private Integer failedLoginAttempts = 0;

    @Column(name = "account_locked_until")
    private Date accountLockedUntil;

    @Column(name = "is_verified", columnDefinition = "boolean default false")
    private Boolean isVerified = false;

    @Column(name = "verification_token", unique = true)
    private String verificationToken;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "last_login")
    private Date lastLogin;

    // Profile
    @Column(name = "name")
    private String name;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role")
    private String role = "user";

    // One-to-Many relationship with Sessions
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}