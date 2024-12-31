package tn.maiko26.springboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import tn.maiko26.springboot.model.relations.TeamMember;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private Date createdAt;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<TeamMember> members;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Project> projects;

    public Team() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public List<TeamMember> getMembers() {
        return this.members;
    }

    public List<Project> getProjects() {
        return this.projects;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Team)) return false;
        final Team other = (Team) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$members = this.getMembers();
        final Object other$members = other.getMembers();
        if (this$members == null ? other$members != null : !this$members.equals(other$members)) return false;
        final Object this$projects = this.getProjects();
        final Object other$projects = other.getProjects();
        if (this$projects == null ? other$projects != null : !this$projects.equals(other$projects)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Team;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $members = this.getMembers();
        result = result * PRIME + ($members == null ? 43 : $members.hashCode());
        final Object $projects = this.getProjects();
        result = result * PRIME + ($projects == null ? 43 : $projects.hashCode());
        return result;
    }

    public String toString() {
        return "Team(id=" + this.getId() + ", name=" + this.getName() + ", createdAt=" + this.getCreatedAt() + ", members=" + this.getMembers() + ", projects=" + this.getProjects() + ")";
    }
}