package tn.maiko26.springboot.models.relations;

import jakarta.persistence.*;
import tn.maiko26.springboot.models.Notification;
import tn.maiko26.springboot.models.User;

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
}

 class UserNotificationId implements Serializable {
    private String user;
    private String notification;
}