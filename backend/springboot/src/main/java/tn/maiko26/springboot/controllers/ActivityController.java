package tn.maiko26.springboot.controllers;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.maiko26.springboot.models.ActivityHistory;
import tn.maiko26.springboot.services.ActivityHistoryService;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityHistoryService activityHistoryService;


    @GetMapping
    public ResponseEntity<?> getAllActivities() {
        try {
            List<ActivityHistory> activities = activityHistoryService.getAllActivities();
            return ResponseEntity.ok().body(activities);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/user/:email")
    public ResponseEntity<?> getUserActivities(@RequestBody String email) {
        try {
            List<ActivityHistory> userActivities = activityHistoryService.getAllUserActivities(email);
            return ResponseEntity.ok().body(userActivities);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
