package tn.maiko26.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {


    private String email;
    private String name;
    private String avatarUrl;
    private String phoneNumber;
    private String role;

}
