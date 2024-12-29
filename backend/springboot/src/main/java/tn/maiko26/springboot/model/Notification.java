package tn.maiko26.springboot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "notifications")
public class Notification {
    @Id
    @Column(name = "notification_id")
    private String id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "read", columnDefinition = "boolean default false")
    private Boolean read = false;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private Date createdAt;

    @Column(name = "link")
    private String link;

    // Relations

    @ManyToMany
    @JoinTable(name = "user_notifications",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email")
    )
    private List<User> users;

}

