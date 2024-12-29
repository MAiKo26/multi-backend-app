package tn.maiko26.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.UserSetting;
import tn.maiko26.springboot.service.ProfileService;

@RestController
@RequestMapping("/profile")

public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping
    public ResponseEntity<?> getUserProfile() {

        User user = profileService.getCurrentUser();
        return ResponseEntity.ok().body(user);


    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody User user) {

        profileService.updateProfile(user);
        return ResponseEntity.ok().body("Successful");


    }

    @PutMapping("/password")
    public ResponseEntity<?> updateProfile(@RequestBody String password) {

        profileService.updatePassword(password);
        return ResponseEntity.ok().body("Successful");


    }

    @PutMapping("/settings")
    public ResponseEntity<?> updateProfile(@RequestBody UserSetting newSetting) {

        profileService.updateNotificationSettings(newSetting);
        return ResponseEntity.ok().body("Successful");


    }


}
