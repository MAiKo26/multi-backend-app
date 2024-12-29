package tn.maiko26.springboot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.maiko26.springboot.enums.UserRole;
import tn.maiko26.springboot.model.relations.*;

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
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    @Column(name = "reset_password_expiry")
    private Date resetPasswordExpiry;
    @Column(name = "failed_login_attempts", columnDefinition = "integer default 0")
    private Integer failedLoginAttempts = 0;
    @Column(name = "account_locked_until")
    private Date accountLockedUntil;
    @Column(name = "is_verified", nullable = false, columnDefinition = "boolean default false")
    private Boolean isVerified = false;
    @Column(name = "verification_token", unique = true)
    private String verificationToken;
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private Date createdAt;
    @Column(name = "last_login")
    private Date lastLogin;

    // Profile
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role", nullable = false)
    private String role;

    // Billing related fields
    @Column(name = "subscription_status")
    private String subscriptionStatus;
    @Column(name = "subscription_id")
    private String subscriptionId;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "mba_points", columnDefinition = "integer default 0")
    private Integer mbaPoints = 0;

    // Notifications preferences
    @Column(name = "email_notifications", columnDefinition = "boolean default true")
    private Boolean emailNotifications = true;
    @Column(name = "push_notifications", columnDefinition = "boolean default true")
    private Boolean pushNotifications = true;


    // Relations



    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserSetting userSettings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Session> sessions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ActivityHistory> activityHistory;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserNotification> userNotifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TeamMember> teamMemberships;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProjectMember> projectMemberships;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<StarredTask> starredTasks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TaskComment> taskComments;


    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}