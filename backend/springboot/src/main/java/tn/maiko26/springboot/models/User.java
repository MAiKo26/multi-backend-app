package tn.maiko26.springboot.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @Column(name = "email")
    private String email;

    // Auth
    @Column(name = "password")
    private String password;

    @Column(name = "resetPasswordToken")
    private String resetPasswordToken;
    @Column(name = "resetPasswordExpiry")
    private String resetPasswordExpiry;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;
    @Column(name = "account_locked_until")
    private Date accountLockedUntil;

    @Column(name = "is_verified")
    private Boolean isVerified;
    @Column(name = "verificationToken")
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
    private String role;




}
