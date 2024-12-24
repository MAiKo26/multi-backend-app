package tn.maiko26.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tn.maiko26.springboot.enums.UserRole;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDto {


    private String email;
    private String name;
    private String avatarUrl;
    private String phoneNumber;
    private UserRole role;
    private Date lastLogin;

}
