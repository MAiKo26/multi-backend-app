package tn.maiko26.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.models.User;
import tn.maiko26.springboot.models.UserSetting;
import tn.maiko26.springboot.services.ProfileService;

@RestController
@RequestMapping("/profile")

public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping
    public ResponseEntity<?> getUserProfile() {
        try {
            User user = profileService.getCurrentUser();
            return ResponseEntity.ok().body(user);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody User user) {
        try {
            profileService.updateProfile(user);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/password")
    public ResponseEntity<?> updateProfile(@RequestBody String password) {
        try {
            profileService.updatePassword(password);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/settings")
    public ResponseEntity<?> updateProfile(@RequestBody UserSetting newSetting) {
        try {
            profileService.updateNotificationSettings(newSetting);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }


}
