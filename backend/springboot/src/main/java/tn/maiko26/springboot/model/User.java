package tn.maiko26.springboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import tn.maiko26.springboot.model.relations.*;

import java.util.Date;
import java.util.List;

@Entity
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
    @JsonBackReference
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

    public User() {
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getResetPasswordToken() {
        return this.resetPasswordToken;
    }

    public Date getResetPasswordExpiry() {
        return this.resetPasswordExpiry;
    }

    public Integer getFailedLoginAttempts() {
        return this.failedLoginAttempts;
    }

    public Date getAccountLockedUntil() {
        return this.accountLockedUntil;
    }

    public Boolean getIsVerified() {
        return this.isVerified;
    }

    public String getVerificationToken() {
        return this.verificationToken;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getLastLogin() {
        return this.lastLogin;
    }

    public String getName() {
        return this.name;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getRole() {
        return this.role;
    }

    public String getSubscriptionStatus() {
        return this.subscriptionStatus;
    }

    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public Integer getMbaPoints() {
        return this.mbaPoints;
    }

    public Boolean getEmailNotifications() {
        return this.emailNotifications;
    }

    public Boolean getPushNotifications() {
        return this.pushNotifications;
    }

    public UserSetting getUserSettings() {
        return this.userSettings;
    }

    public List<Session> getSessions() {
        return this.sessions;
    }

    public List<ActivityHistory> getActivityHistory() {
        return this.activityHistory;
    }

    public List<UserNotification> getUserNotifications() {
        return this.userNotifications;
    }

    public List<TeamMember> getTeamMemberships() {
        return this.teamMemberships;
    }

    public List<ProjectMember> getProjectMemberships() {
        return this.projectMemberships;
    }

    public List<StarredTask> getStarredTasks() {
        return this.starredTasks;
    }

    public List<TaskComment> getTaskComments() {
        return this.taskComments;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public void setResetPasswordExpiry(Date resetPasswordExpiry) {
        this.resetPasswordExpiry = resetPasswordExpiry;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public void setAccountLockedUntil(Date accountLockedUntil) {
        this.accountLockedUntil = accountLockedUntil;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setMbaPoints(Integer mbaPoints) {
        this.mbaPoints = mbaPoints;
    }

    public void setEmailNotifications(Boolean emailNotifications) {
        this.emailNotifications = emailNotifications;
    }

    public void setPushNotifications(Boolean pushNotifications) {
        this.pushNotifications = pushNotifications;
    }

    public void setUserSettings(UserSetting userSettings) {
        this.userSettings = userSettings;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public void setActivityHistory(List<ActivityHistory> activityHistory) {
        this.activityHistory = activityHistory;
    }

    public void setUserNotifications(List<UserNotification> userNotifications) {
        this.userNotifications = userNotifications;
    }

    public void setTeamMemberships(List<TeamMember> teamMemberships) {
        this.teamMemberships = teamMemberships;
    }

    public void setProjectMemberships(List<ProjectMember> projectMemberships) {
        this.projectMemberships = projectMemberships;
    }

    public void setStarredTasks(List<StarredTask> starredTasks) {
        this.starredTasks = starredTasks;
    }

    public void setTaskComments(List<TaskComment> taskComments) {
        this.taskComments = taskComments;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$resetPasswordToken = this.getResetPasswordToken();
        final Object other$resetPasswordToken = other.getResetPasswordToken();
        if (this$resetPasswordToken == null ? other$resetPasswordToken != null : !this$resetPasswordToken.equals(other$resetPasswordToken))
            return false;
        final Object this$resetPasswordExpiry = this.getResetPasswordExpiry();
        final Object other$resetPasswordExpiry = other.getResetPasswordExpiry();
        if (this$resetPasswordExpiry == null ? other$resetPasswordExpiry != null : !this$resetPasswordExpiry.equals(other$resetPasswordExpiry))
            return false;
        final Object this$failedLoginAttempts = this.getFailedLoginAttempts();
        final Object other$failedLoginAttempts = other.getFailedLoginAttempts();
        if (this$failedLoginAttempts == null ? other$failedLoginAttempts != null : !this$failedLoginAttempts.equals(other$failedLoginAttempts))
            return false;
        final Object this$accountLockedUntil = this.getAccountLockedUntil();
        final Object other$accountLockedUntil = other.getAccountLockedUntil();
        if (this$accountLockedUntil == null ? other$accountLockedUntil != null : !this$accountLockedUntil.equals(other$accountLockedUntil))
            return false;
        final Object this$isVerified = this.getIsVerified();
        final Object other$isVerified = other.getIsVerified();
        if (this$isVerified == null ? other$isVerified != null : !this$isVerified.equals(other$isVerified))
            return false;
        final Object this$verificationToken = this.getVerificationToken();
        final Object other$verificationToken = other.getVerificationToken();
        if (this$verificationToken == null ? other$verificationToken != null : !this$verificationToken.equals(other$verificationToken))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$lastLogin = this.getLastLogin();
        final Object other$lastLogin = other.getLastLogin();
        if (this$lastLogin == null ? other$lastLogin != null : !this$lastLogin.equals(other$lastLogin)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$avatarUrl = this.getAvatarUrl();
        final Object other$avatarUrl = other.getAvatarUrl();
        if (this$avatarUrl == null ? other$avatarUrl != null : !this$avatarUrl.equals(other$avatarUrl)) return false;
        final Object this$phoneNumber = this.getPhoneNumber();
        final Object other$phoneNumber = other.getPhoneNumber();
        if (this$phoneNumber == null ? other$phoneNumber != null : !this$phoneNumber.equals(other$phoneNumber))
            return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        final Object this$subscriptionStatus = this.getSubscriptionStatus();
        final Object other$subscriptionStatus = other.getSubscriptionStatus();
        if (this$subscriptionStatus == null ? other$subscriptionStatus != null : !this$subscriptionStatus.equals(other$subscriptionStatus))
            return false;
        final Object this$subscriptionId = this.getSubscriptionId();
        final Object other$subscriptionId = other.getSubscriptionId();
        if (this$subscriptionId == null ? other$subscriptionId != null : !this$subscriptionId.equals(other$subscriptionId))
            return false;
        final Object this$customerId = this.getCustomerId();
        final Object other$customerId = other.getCustomerId();
        if (this$customerId == null ? other$customerId != null : !this$customerId.equals(other$customerId))
            return false;
        final Object this$mbaPoints = this.getMbaPoints();
        final Object other$mbaPoints = other.getMbaPoints();
        if (this$mbaPoints == null ? other$mbaPoints != null : !this$mbaPoints.equals(other$mbaPoints)) return false;
        final Object this$emailNotifications = this.getEmailNotifications();
        final Object other$emailNotifications = other.getEmailNotifications();
        if (this$emailNotifications == null ? other$emailNotifications != null : !this$emailNotifications.equals(other$emailNotifications))
            return false;
        final Object this$pushNotifications = this.getPushNotifications();
        final Object other$pushNotifications = other.getPushNotifications();
        if (this$pushNotifications == null ? other$pushNotifications != null : !this$pushNotifications.equals(other$pushNotifications))
            return false;
        final Object this$userSettings = this.getUserSettings();
        final Object other$userSettings = other.getUserSettings();
        if (this$userSettings == null ? other$userSettings != null : !this$userSettings.equals(other$userSettings))
            return false;
        final Object this$sessions = this.getSessions();
        final Object other$sessions = other.getSessions();
        if (this$sessions == null ? other$sessions != null : !this$sessions.equals(other$sessions)) return false;
        final Object this$activityHistory = this.getActivityHistory();
        final Object other$activityHistory = other.getActivityHistory();
        if (this$activityHistory == null ? other$activityHistory != null : !this$activityHistory.equals(other$activityHistory))
            return false;
        final Object this$userNotifications = this.getUserNotifications();
        final Object other$userNotifications = other.getUserNotifications();
        if (this$userNotifications == null ? other$userNotifications != null : !this$userNotifications.equals(other$userNotifications))
            return false;
        final Object this$teamMemberships = this.getTeamMemberships();
        final Object other$teamMemberships = other.getTeamMemberships();
        if (this$teamMemberships == null ? other$teamMemberships != null : !this$teamMemberships.equals(other$teamMemberships))
            return false;
        final Object this$projectMemberships = this.getProjectMemberships();
        final Object other$projectMemberships = other.getProjectMemberships();
        if (this$projectMemberships == null ? other$projectMemberships != null : !this$projectMemberships.equals(other$projectMemberships))
            return false;
        final Object this$starredTasks = this.getStarredTasks();
        final Object other$starredTasks = other.getStarredTasks();
        if (this$starredTasks == null ? other$starredTasks != null : !this$starredTasks.equals(other$starredTasks))
            return false;
        final Object this$taskComments = this.getTaskComments();
        final Object other$taskComments = other.getTaskComments();
        if (this$taskComments == null ? other$taskComments != null : !this$taskComments.equals(other$taskComments))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $resetPasswordToken = this.getResetPasswordToken();
        result = result * PRIME + ($resetPasswordToken == null ? 43 : $resetPasswordToken.hashCode());
        final Object $resetPasswordExpiry = this.getResetPasswordExpiry();
        result = result * PRIME + ($resetPasswordExpiry == null ? 43 : $resetPasswordExpiry.hashCode());
        final Object $failedLoginAttempts = this.getFailedLoginAttempts();
        result = result * PRIME + ($failedLoginAttempts == null ? 43 : $failedLoginAttempts.hashCode());
        final Object $accountLockedUntil = this.getAccountLockedUntil();
        result = result * PRIME + ($accountLockedUntil == null ? 43 : $accountLockedUntil.hashCode());
        final Object $isVerified = this.getIsVerified();
        result = result * PRIME + ($isVerified == null ? 43 : $isVerified.hashCode());
        final Object $verificationToken = this.getVerificationToken();
        result = result * PRIME + ($verificationToken == null ? 43 : $verificationToken.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $lastLogin = this.getLastLogin();
        result = result * PRIME + ($lastLogin == null ? 43 : $lastLogin.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $avatarUrl = this.getAvatarUrl();
        result = result * PRIME + ($avatarUrl == null ? 43 : $avatarUrl.hashCode());
        final Object $phoneNumber = this.getPhoneNumber();
        result = result * PRIME + ($phoneNumber == null ? 43 : $phoneNumber.hashCode());
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        final Object $subscriptionStatus = this.getSubscriptionStatus();
        result = result * PRIME + ($subscriptionStatus == null ? 43 : $subscriptionStatus.hashCode());
        final Object $subscriptionId = this.getSubscriptionId();
        result = result * PRIME + ($subscriptionId == null ? 43 : $subscriptionId.hashCode());
        final Object $customerId = this.getCustomerId();
        result = result * PRIME + ($customerId == null ? 43 : $customerId.hashCode());
        final Object $mbaPoints = this.getMbaPoints();
        result = result * PRIME + ($mbaPoints == null ? 43 : $mbaPoints.hashCode());
        final Object $emailNotifications = this.getEmailNotifications();
        result = result * PRIME + ($emailNotifications == null ? 43 : $emailNotifications.hashCode());
        final Object $pushNotifications = this.getPushNotifications();
        result = result * PRIME + ($pushNotifications == null ? 43 : $pushNotifications.hashCode());
        final Object $userSettings = this.getUserSettings();
        result = result * PRIME + ($userSettings == null ? 43 : $userSettings.hashCode());
        final Object $sessions = this.getSessions();
        result = result * PRIME + ($sessions == null ? 43 : $sessions.hashCode());
        final Object $activityHistory = this.getActivityHistory();
        result = result * PRIME + ($activityHistory == null ? 43 : $activityHistory.hashCode());
        final Object $userNotifications = this.getUserNotifications();
        result = result * PRIME + ($userNotifications == null ? 43 : $userNotifications.hashCode());
        final Object $teamMemberships = this.getTeamMemberships();
        result = result * PRIME + ($teamMemberships == null ? 43 : $teamMemberships.hashCode());
        final Object $projectMemberships = this.getProjectMemberships();
        result = result * PRIME + ($projectMemberships == null ? 43 : $projectMemberships.hashCode());
        final Object $starredTasks = this.getStarredTasks();
        result = result * PRIME + ($starredTasks == null ? 43 : $starredTasks.hashCode());
        final Object $taskComments = this.getTaskComments();
        result = result * PRIME + ($taskComments == null ? 43 : $taskComments.hashCode());
        return result;
    }

    public String toString() {
        return "User(email=" + this.getEmail() + ", password=" + this.getPassword() + ", resetPasswordToken=" + this.getResetPasswordToken() + ", resetPasswordExpiry=" + this.getResetPasswordExpiry() + ", failedLoginAttempts=" + this.getFailedLoginAttempts() + ", accountLockedUntil=" + this.getAccountLockedUntil() + ", isVerified=" + this.getIsVerified() + ", verificationToken=" + this.getVerificationToken() + ", createdAt=" + this.getCreatedAt() + ", lastLogin=" + this.getLastLogin() + ", name=" + this.getName() + ", avatarUrl=" + this.getAvatarUrl() + ", phoneNumber=" + this.getPhoneNumber() + ", role=" + this.getRole() + ", subscriptionStatus=" + this.getSubscriptionStatus() + ", subscriptionId=" + this.getSubscriptionId() + ", customerId=" + this.getCustomerId() + ", mbaPoints=" + this.getMbaPoints() + ", emailNotifications=" + this.getEmailNotifications() + ", pushNotifications=" + this.getPushNotifications() + ", userSettings=" + this.getUserSettings() + ", sessions=" + this.getSessions() + ", activityHistory=" + this.getActivityHistory() + ", userNotifications=" + this.getUserNotifications() + ", teamMemberships=" + this.getTeamMemberships() + ", projectMemberships=" + this.getProjectMemberships() + ", starredTasks=" + this.getStarredTasks() + ", taskComments=" + this.getTaskComments() + ")";
    }
}