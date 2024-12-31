package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.Query;
import tn.maiko26.springboot.dto.UserWithSessionDto;
import tn.maiko26.springboot.model.Session;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.User;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, String> {
    void deleteBySessionId(String sessionId);

    @Query("SELECT (s.sessionId, u.email, u.name, u.avatarUrl, u.role, u.phoneNumber, u.lastLogin) " +
            "FROM Session s INNER JOIN User u ON s.user.email = u.email " +
            "WHERE s.sessionId = :sessionId")
    Optional<UserWithSessionDto> findUserDetailsBySession(String sessionId);
}
