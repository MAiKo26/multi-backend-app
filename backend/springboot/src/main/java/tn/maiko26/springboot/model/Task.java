package tn.maiko26.springboot.model;

import jakarta.persistence.*;
import tn.maiko26.springboot.model.relations.StarredTask;
import tn.maiko26.springboot.model.relations.TaskComment;

import java.util.Date;
import java.util.List;

@Entity
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

    public Task(String id, String name, String description, Date createdAt, Integer stars, Boolean finished, Project project, User finishedBy, List<TaskComment> comments, List<StarredTask> starredBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.stars = stars;
        this.finished = finished;
        this.project = project;
        this.finishedBy = finishedBy;
        this.comments = comments;
        this.starredBy = starredBy;
    }

    public Task() {
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Integer getStars() {
        return this.stars;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public Project getProject() {
        return this.project;
    }

    public User getFinishedBy() {
        return this.finishedBy;
    }

    public List<TaskComment> getComments() {
        return this.comments;
    }

    public List<StarredTask> getStarredBy() {
        return this.starredBy;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setFinishedBy(User finishedBy) {
        this.finishedBy = finishedBy;
    }

    public void setComments(List<TaskComment> comments) {
        this.comments = comments;
    }

    public void setStarredBy(List<StarredTask> starredBy) {
        this.starredBy = starredBy;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Task)) return false;
        final Task other = (Task) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$stars = this.getStars();
        final Object other$stars = other.getStars();
        if (this$stars == null ? other$stars != null : !this$stars.equals(other$stars)) return false;
        final Object this$finished = this.getFinished();
        final Object other$finished = other.getFinished();
        if (this$finished == null ? other$finished != null : !this$finished.equals(other$finished)) return false;
        final Object this$project = this.getProject();
        final Object other$project = other.getProject();
        if (this$project == null ? other$project != null : !this$project.equals(other$project)) return false;
        final Object this$finishedBy = this.getFinishedBy();
        final Object other$finishedBy = other.getFinishedBy();
        if (this$finishedBy == null ? other$finishedBy != null : !this$finishedBy.equals(other$finishedBy))
            return false;
        final Object this$comments = this.getComments();
        final Object other$comments = other.getComments();
        if (this$comments == null ? other$comments != null : !this$comments.equals(other$comments)) return false;
        final Object this$starredBy = this.getStarredBy();
        final Object other$starredBy = other.getStarredBy();
        if (this$starredBy == null ? other$starredBy != null : !this$starredBy.equals(other$starredBy)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Task;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $stars = this.getStars();
        result = result * PRIME + ($stars == null ? 43 : $stars.hashCode());
        final Object $finished = this.getFinished();
        result = result * PRIME + ($finished == null ? 43 : $finished.hashCode());
        final Object $project = this.getProject();
        result = result * PRIME + ($project == null ? 43 : $project.hashCode());
        final Object $finishedBy = this.getFinishedBy();
        result = result * PRIME + ($finishedBy == null ? 43 : $finishedBy.hashCode());
        final Object $comments = this.getComments();
        result = result * PRIME + ($comments == null ? 43 : $comments.hashCode());
        final Object $starredBy = this.getStarredBy();
        result = result * PRIME + ($starredBy == null ? 43 : $starredBy.hashCode());
        return result;
    }

    public String toString() {
        return "Task(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", createdAt=" + this.getCreatedAt() + ", stars=" + this.getStars() + ", finished=" + this.getFinished() + ", project=" + this.getProject() + ", finishedBy=" + this.getFinishedBy() + ", comments=" + this.getComments() + ", starredBy=" + this.getStarredBy() + ")";
    }
}
