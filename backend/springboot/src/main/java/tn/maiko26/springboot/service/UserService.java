package tn.maiko26.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.CustomException;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.repository.SessionRepository;
import tn.maiko26.springboot.repository.TeamMemberRepository;
import tn.maiko26.springboot.repository.UserRepository;
import tn.maiko26.springboot.dto.UserWithSessionDto;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private SessionRepository sessionRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get all users by team
    public List<User> getAllUsersByTeam(String teamId) {
        return teamMemberRepository.findAllByTeamIdWithUsers(teamId);
    }

    // Get user details by session
    public UserWithSessionDto getUserDetailsBySession(String sessionId) {
        return sessionRepository.findUserDetailsBySession(sessionId)
                .map(userWithSession -> {
                    UserWithSessionDto dto = new UserWithSessionDto();
                    dto.setSessionId(userWithSession.getSessionId());
                    dto.setEmail(userWithSession.getEmail());
                    dto.setName(userWithSession.getName());
                    dto.setAvatar(userWithSession.getAvatar());
                    dto.setRole(userWithSession.getRole());
                    dto.setPhoneNumber(userWithSession.getPhoneNumber());
                    dto.setLastLogin(userWithSession.getLastLogin());
                    return dto;
                })
                .orElseThrow(() -> new CustomException("Session not found", 400));
    }

    // Get current logged-in user email
    public String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    // Get user by email
    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User doesn't exist", 400));
    }
}
