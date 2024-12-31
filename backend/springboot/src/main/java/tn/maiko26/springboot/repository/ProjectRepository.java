package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.Project;
import tn.maiko26.springboot.model.Session;
import tn.maiko26.springboot.model.Team;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, String> {
    Optional<Project> findById(String project_id);

    List<Project> findByTeam(Team team);
}
