package tn.maiko26.springboot.dto.mappers;

import org.springframework.stereotype.Component;
import tn.maiko26.springboot.dto.UserDto;
import tn.maiko26.springboot.model.User;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        if (user == null) return null;
        return new UserDto(
                user.getEmail(),
                user.getName(),
                user.getAvatarUrl(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getLastLogin()
        );
    }
}