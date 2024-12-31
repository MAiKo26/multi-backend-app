package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.Session;
import tn.maiko26.springboot.model.Team;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.relations.TeamMember;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, String> {

    List<TeamMember> findAllByTeam(Team team);
}
