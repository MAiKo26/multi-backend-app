package tn.maiko26.springboot.models.relations;

import jakarta.persistence.*;
import tn.maiko26.springboot.models.Team;
import tn.maiko26.springboot.models.User;

import java.io.Serializable;

@Entity
@Table(name = "team_members")
@IdClass(TeamMemberId.class)
public class TeamMember {
    @Id
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Id
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private User user;
}

class TeamMemberId implements Serializable {
    private Integer team;
    private String user;
}