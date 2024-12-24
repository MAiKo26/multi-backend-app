package tn.maiko26.springboot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.maiko26.springboot.models.relations.StarredTask;
import tn.maiko26.springboot.models.relations.TaskComment;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "task_id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private Date createdAt;

    @Column(name = "stars", columnDefinition = "integer default 0")
    private Integer stars = 0;

    @Column(name = "finished", nullable = false, columnDefinition = "boolean default false")
    private Boolean finished = false;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "finished_by", referencedColumnName = "email")
    private User finishedBy;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskComment> comments;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<StarredTask> starredBy;
}
