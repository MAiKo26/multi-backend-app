package tn.maiko26.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithSessionDto {
    private String sessionId;
    private String email;
    private String name;
    private String avatar;
    private String role;
    private String phoneNumber;
    private String lastLogin;


}
