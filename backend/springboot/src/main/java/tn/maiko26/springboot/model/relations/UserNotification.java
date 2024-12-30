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
}

 class UserNotificationId implements Serializable {
    private String user;
    private String notification;
}