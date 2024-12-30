package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.Session;
import tn.maiko26.springboot.model.relations.ProjectMember;

import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, String> {
    Optional<ProjectMember> findByProjectIdAndEmail(String projectId, String email);
}
