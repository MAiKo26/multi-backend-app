package tn.maiko26.springboot.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
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
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "email")
    private User user;

    public ActivityHistory(String id, String description, Date doneAt, User user) {
        this.id = id;
        this.description = description;
        this.doneAt = doneAt;
        this.user = user;
    }

    public ActivityHistory() {
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDoneAt() {
        return this.doneAt;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDoneAt(Date doneAt) {
        this.doneAt = doneAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ActivityHistory)) return false;
        final ActivityHistory other = (ActivityHistory) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$doneAt = this.getDoneAt();
        final Object other$doneAt = other.getDoneAt();
        if (this$doneAt == null ? other$doneAt != null : !this$doneAt.equals(other$doneAt)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ActivityHistory;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $doneAt = this.getDoneAt();
        result = result * PRIME + ($doneAt == null ? 43 : $doneAt.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        return result;
    }

    public String toString() {
        return "ActivityHistory(id=" + this.getId() + ", description=" + this.getDescription() + ", doneAt=" + this.getDoneAt() + ", user=" + this.getUser() + ")";
    }
}
