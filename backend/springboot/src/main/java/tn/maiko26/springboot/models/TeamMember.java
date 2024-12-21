package tn.maiko26.springboot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "team_members")
public class TeamMember {

    @Id
    @Column(name = "team_member_id")
    private String id;

    @ManyToOne // Many team members belong to one team
    @JoinColumn(name = "team_id", nullable = false) // Defines the foreign key column
    private Team team;

    @ManyToOne // Many team members belong to one user (email is unique in your system)
    @JoinColumn(name = "email", nullable = false) // Defines the foreign key column
    private User user;

    @Column(name = "role", columnDefinition = "varchar(255) default 'user'")
    private String role;
}
