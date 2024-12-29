package tn.maiko26.springboot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,referencedColumnName = "email")
    private User user;

}
