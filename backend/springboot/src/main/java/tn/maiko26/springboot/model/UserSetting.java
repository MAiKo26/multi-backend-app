package tn.maiko26.springboot.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
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
}
