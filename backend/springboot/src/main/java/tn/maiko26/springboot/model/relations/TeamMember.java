package tn.maiko26.springboot.model.relations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import tn.maiko26.springboot.model.Team;
import tn.maiko26.springboot.model.User;

import java.io.Serializable;

@Entity
@Table(name = "team_members")
@IdClass(TeamMemberId.class)
public class TeamMember {
    @Id
    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    @Id
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    @JsonBackReference
    private User user;

    public TeamMember(Team team, User user) {
        this.team = team;
        this.user = user;
    }

    public TeamMember() {
    }

    public Team getTeam() {
        return this.team;
    }

    public User getUser() {
        return this.user;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TeamMember)) return false;
        final TeamMember other = (TeamMember) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$team = this.getTeam();
        final Object other$team = other.getTeam();
        if (this$team == null ? other$team != null : !this$team.equals(other$team)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TeamMember;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $team = this.getTeam();
        result = result * PRIME + ($team == null ? 43 : $team.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        return result;
    }

    public String toString() {
        return "TeamMember(team=" + this.getTeam() + ", user=" + this.getUser() + ")";
    }
}

class TeamMemberId implements Serializable {
    private Integer team;
    private String user;
}