package tn.maiko26.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.maiko26.springboot.dto.UserDto;
import tn.maiko26.springboot.dto.mappers.UserMapper;
import tn.maiko26.springboot.models.User;
import tn.maiko26.springboot.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private  UserMapper userMapper;



    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> userDtos = users
                    .stream()
                    .map(userMapper::toDto)
                    .toList();
            return ResponseEntity.ok().body(userDtos);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Internal Server Error: "));

        }
    }

}
