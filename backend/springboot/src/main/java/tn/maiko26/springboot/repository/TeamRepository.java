package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.maiko26.springboot.model.Session;
import tn.maiko26.springboot.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String> {

    @Query("SELECT t FROM Team t " +
            "JOIN t.teamMembers tm " +
            "WHERE tm.email = :email")
    List<Team> findTeamsByUserEmail(@Param("email") String email);
}
