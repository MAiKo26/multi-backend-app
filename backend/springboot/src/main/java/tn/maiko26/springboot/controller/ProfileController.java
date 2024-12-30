package tn.maiko26.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.UserSetting;
import tn.maiko26.springboot.service.ProfileService;

@RestController
@RequestMapping("/profile")
@PreAuthorize("hasAnyRole('user','admin')")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // Get User Profile
    @GetMapping
    public ResponseEntity<User> getUserProfile() {
        User user = profileService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    // Update User Profile
    @PutMapping
    public ResponseEntity<String> updateProfile(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String avatarPath
    ) {
        profileService.updateProfile(name, phoneNumber, avatarPath);
        return ResponseEntity.ok("Profile updated successfully");
    }

    // Update User Password
    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword
    ) {
        profileService.updatePassword(currentPassword, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

    // Update Notification Settings
    @PutMapping("/settings")
    public ResponseEntity<String> updateNotificationSettings(
            @RequestBody UserSetting newSetting
    ) {
        profileService.updateNotificationSettings(newSetting);
        return ResponseEntity.ok("Notification settings updated successfully");
    }
}
