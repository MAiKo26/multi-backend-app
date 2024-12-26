package tn.maiko26.springboot.services;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.models.User;
import tn.maiko26.springboot.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllUsersByTeam(String teamId) {
        throw new NotImplementedException();

    }

    public User getUserDetailsBySession(String sessionId) {
        throw new NotImplementedException();

    }

}
