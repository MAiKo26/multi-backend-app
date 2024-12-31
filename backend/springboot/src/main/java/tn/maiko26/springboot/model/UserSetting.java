package tn.maiko26.springboot.model;


import jakarta.persistence.*;


@Entity
@Table(name = "user_settings")
public class UserSetting {
    @Id
    @Column(name = "user_email")
    private String userEmail;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_email")
    private User user;

    @Column(name = "email_digest", nullable = false, columnDefinition = "boolean default true")
    private Boolean emailDigest = true;

    @Column(name = "task_reminders", nullable = false, columnDefinition = "boolean default true")
    private Boolean taskReminders = true;

    public UserSetting(User user) {
        this.user = user;
        this.userEmail = user.getEmail();
        this.emailDigest = true;
        this.taskReminders = true;
    }

    public UserSetting(String userEmail, User user, Boolean emailDigest, Boolean taskReminders) {
        this.userEmail = userEmail;
        this.user = user;
        this.emailDigest = emailDigest;
        this.taskReminders = taskReminders;
    }

    public UserSetting() {
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public User getUser() {
        return this.user;
    }

    public Boolean getEmailDigest() {
        return this.emailDigest;
    }

    public Boolean getTaskReminders() {
        return this.taskReminders;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEmailDigest(Boolean emailDigest) {
        this.emailDigest = emailDigest;
    }

    public void setTaskReminders(Boolean taskReminders) {
        this.taskReminders = taskReminders;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserSetting)) return false;
        final UserSetting other = (UserSetting) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userEmail = this.getUserEmail();
        final Object other$userEmail = other.getUserEmail();
        if (this$userEmail == null ? other$userEmail != null : !this$userEmail.equals(other$userEmail)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$emailDigest = this.getEmailDigest();
        final Object other$emailDigest = other.getEmailDigest();
        if (this$emailDigest == null ? other$emailDigest != null : !this$emailDigest.equals(other$emailDigest))
            return false;
        final Object this$taskReminders = this.getTaskReminders();
        final Object other$taskReminders = other.getTaskReminders();
        if (this$taskReminders == null ? other$taskReminders != null : !this$taskReminders.equals(other$taskReminders))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserSetting;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userEmail = this.getUserEmail();
        result = result * PRIME + ($userEmail == null ? 43 : $userEmail.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $emailDigest = this.getEmailDigest();
        result = result * PRIME + ($emailDigest == null ? 43 : $emailDigest.hashCode());
        final Object $taskReminders = this.getTaskReminders();
        result = result * PRIME + ($taskReminders == null ? 43 : $taskReminders.hashCode());
        return result;
    }

    public String toString() {
        return "UserSetting(userEmail=" + this.getUserEmail() + ", user=" + this.getUser() + ", emailDigest=" + this.getEmailDigest() + ", taskReminders=" + this.getTaskReminders() + ")";
    }
}
