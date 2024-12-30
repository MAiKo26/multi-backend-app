package tn.maiko26.springboot.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.maiko26.springboot.dto.ActivityHistoryDto;
import tn.maiko26.springboot.model.ActivityHistory;
import tn.maiko26.springboot.service.ActivityHistoryService;

import java.util.List;

@RestController
@RequestMapping("/activity")
@PreAuthorize("hasAnyRole('user','admin')")
public class ActivityController {

    @Autowired
    private ActivityHistoryService activityHistoryService;


    @GetMapping
    public ResponseEntity<?> getAllActivities() {

        List<ActivityHistory> activities = activityHistoryService.getAllActivities();
        List<ActivityHistoryDto> activitiesDto = activities.stream().map(ActivityHistoryDto::new).toList();


        return ResponseEntity.ok().body(activitiesDto);


    }

    @GetMapping("/user/:email")
    public ResponseEntity<?> getUserActivities(@RequestBody String email) {

        List<ActivityHistory> userActivities = activityHistoryService.getAllUserActivities(email);

        List<ActivityHistoryDto> userActivitiesDto = userActivities.stream().map(ActivityHistoryDto::new).toList();

        return ResponseEntity.ok().body(userActivitiesDto);

    }


}
