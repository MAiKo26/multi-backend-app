package tn.maiko26.springboot.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.CustomException;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.UserSetting;
import tn.maiko26.springboot.repository.UserRepository;
import tn.maiko26.springboot.repository.UserSettingRepository;

@Service
public class ProfileService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserSettingRepository userSettingRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(UserService userService, UserRepository userRepository, UserSettingRepository userSettingRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userSettingRepository = userSettingRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public User getCurrentUser() {
        String email = userService.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", 400));
    }


    public User updateProfile(String name, String phoneNumber, String avatarPath) {
        User user = getCurrentUser();

        if (name != null) user.setName(name);
        if (phoneNumber != null) user.setPhoneNumber(phoneNumber);
        if (avatarPath != null) user.setAvatarUrl(avatarPath);

        return userRepository.save(user);
    }


    public void updatePassword(String currentPassword, String newPassword) {
        User user = getCurrentUser();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new CustomException("Current password is incorrect", 400);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    public void updateNotificationSettings(UserSetting newSetting) {
        User user = getCurrentUser();
        UserSetting currentSetting = userSettingRepository.findByUser(user)
                .orElse(new UserSetting(user));


        if (newSetting.getEmailDigest() != null) {
            currentSetting.setEmailDigest(newSetting.getEmailDigest());
        }
        if (newSetting.getTaskReminders() != null) {
            currentSetting.setTaskReminders(newSetting.getTaskReminders());
        }

        userSettingRepository.save(currentSetting);
    }
}
