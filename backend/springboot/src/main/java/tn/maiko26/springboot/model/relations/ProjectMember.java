package tn.maiko26.springboot.model.relations;

import jakarta.persistence.*;
import tn.maiko26.springboot.model.Project;
import tn.maiko26.springboot.model.User;

import java.io.Serializable;

@Entity
@Table(name = "project_members")
@IdClass(ProjectMemberId.class)
public class ProjectMember {
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Id
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private User user;
}

class ProjectMemberId implements Serializable {
    private String project;
    private String user;
}