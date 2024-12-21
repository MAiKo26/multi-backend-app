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

    @Column(name = "created_at", columnDefinition = "timestamp default now()")
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

    @Column(name = "subscription_status", columnDefinition = "varchar(255) default 'free'")
    private String subscriptionStatus = "free";

    @Column(name = "subscription_id")
    private String subscriptionId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "mba_points", columnDefinition = "integer default 0")
    private Integer mbaPoints = 0;

    @Column(name = "email_notifications", columnDefinition = "boolean default true")
    private Boolean emailNotifications = true;

    @Column(name = "push_notifications", columnDefinition = "boolean default true")
    private Boolean pushNotifications = true;

    // One-to-Many relationship with Sessions
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}