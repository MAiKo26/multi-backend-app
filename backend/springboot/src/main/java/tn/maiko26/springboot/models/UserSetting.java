package tn.maiko26.springboot.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_settings")
public class UserSetting {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    private User user;

    @Column(name = "email_digest", columnDefinition = "boolean default true")
    private Boolean emailDigest = true;

    @Column(name = "task_reminders", columnDefinition = "boolean default true")
    private Boolean taskReminders = true;
}
