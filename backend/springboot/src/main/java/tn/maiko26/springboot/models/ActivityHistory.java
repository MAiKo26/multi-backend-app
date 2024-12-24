package tn.maiko26.springboot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "activity_history")
public class ActivityHistory {
    @Id
    @Column(name = "history_id")
    private String id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "done_at", nullable = false, columnDefinition = "timestamp default now()")
    private Date doneAt;

    // Relations

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    private User user;
}
