package tn.maiko26.springboot.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.maiko26.springboot.models.relations.TeamMember;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
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
    private List<TeamMember> members;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Project> projects;
}