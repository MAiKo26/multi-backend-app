package tn.maiko26.springboot.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.ResourceNotImplementedException;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.UserSetting;

@Service
public class ProfileService {

    public User getCurrentUser() {
        throw new ResourceNotImplementedException();
    }

    public void updateProfile(User user) {
        throw new ResourceNotImplementedException();
    }

    public void updatePassword(String password) {
        throw new ResourceNotImplementedException();

    }

    public void updateNotificationSettings(UserSetting newSetting){
        throw new ResourceNotImplementedException();

    }
}
