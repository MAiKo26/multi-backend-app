package tn.maiko26.springboot.model.relations;

import jakarta.persistence.*;
import tn.maiko26.springboot.model.Task;
import tn.maiko26.springboot.model.User;

import java.util.Date;

@Entity
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

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;
}
