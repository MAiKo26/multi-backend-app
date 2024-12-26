package tn.maiko26.springboot.services;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.models.User;
import tn.maiko26.springboot.models.UserSetting;

@Service
public class ProfileService {

    public User getCurrentUser() {
        throw new NotImplementedException();
    }

    public void updateProfile(User user) {
        throw new NotImplementedException();
    }

    public void updatePassword(String password) {
        throw new NotImplementedException();

    }

    public void updateNotificationSettings(UserSetting newSetting){
        throw new NotImplementedException();

    }
}
