package tn.maiko26.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.maiko26.springboot.model.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private String email;
    private String name;
    private String avatarUrl;
    private String phoneNumber;
    private String role;
    private Date lastLogin;


    public UserDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.avatarUrl = user.getAvatarUrl();
    }
}
