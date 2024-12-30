package tn.maiko26.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.dto.ActivityHistoryDto;
import tn.maiko26.springboot.exception.ResourceNotImplementedException;
import tn.maiko26.springboot.model.ActivityHistory;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.repository.ActivityHistoryRepository;
import tn.maiko26.springboot.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActivityHistoryService {

    @Autowired
    ActivityHistoryRepository activityHistoryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public List<ActivityHistory> getAllActivities() {
        return activityHistoryRepository.findAllWithUsersOrderByDoneAt();

    }

    public List<ActivityHistory> getAllUserActivities(String email) {

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        return activityHistoryRepository.findAllWithUsersOrderByDoneAtWhereUser(email);
    }

    public void logActivity(String userId, String description) {
        try {
            User user = userService.getUserByEmail(userId);
            ActivityHistory activityHistory = new ActivityHistory();
            activityHistory.setId(UUID.randomUUID().toString());
            activityHistory.setUser(user);
            activityHistory.setDescription(description);
            activityHistory.setDoneAt(new Date());

            activityHistoryRepository.save(activityHistory);
        } catch (Exception e) {
            System.err.println("Failed to log activity: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
