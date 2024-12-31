package tn.maiko26.springboot.model.relations;

import jakarta.persistence.*;
import tn.maiko26.springboot.model.Notification;
import tn.maiko26.springboot.model.User;

import java.io.Serializable;

@Entity
@Table(name = "user_notifications")
@IdClass(UserNotificationId.class)
public class UserNotification {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    public UserNotification(User user, Notification notification) {
        this.user = user;
        this.notification = notification;
    }

    public UserNotification() {
    }

    public User getUser() {
        return this.user;
    }

    public Notification getNotification() {
        return this.notification;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserNotification)) return false;
        final UserNotification other = (UserNotification) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$notification = this.getNotification();
        final Object other$notification = other.getNotification();
        if (this$notification == null ? other$notification != null : !this$notification.equals(other$notification))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserNotification;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $notification = this.getNotification();
        result = result * PRIME + ($notification == null ? 43 : $notification.hashCode());
        return result;
    }

    public String toString() {
        return "UserNotification(user=" + this.getUser() + ", notification=" + this.getNotification() + ")";
    }
}

class UserNotificationId implements Serializable {
    private String user;
    private String notification;
}