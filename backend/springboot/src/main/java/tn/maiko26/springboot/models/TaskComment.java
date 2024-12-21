package tn.maiko26.springboot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "task_comments")
public class TaskComment {
    @Id
    @Column(name = "comment_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private Date createdAt;
}