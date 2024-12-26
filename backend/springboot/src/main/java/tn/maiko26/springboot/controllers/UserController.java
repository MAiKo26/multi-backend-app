package tn.maiko26.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.maiko26.springboot.dto.UserDto;
import tn.maiko26.springboot.dto.mappers.UserMapper;
import tn.maiko26.springboot.models.User;
import tn.maiko26.springboot.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    @GetMapping
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

    @GetMapping("/byteam/:teamId")
    public ResponseEntity<?> getAllUsersByTeam(@RequestParam String teamId) {
        try {
            List<User> users = userService.getAllUsersByTeam(teamId);

            return ResponseEntity.ok().body(users);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Internal Server Error: "));

        }
    }


    @GetMapping("/bysession/:session")
    public ResponseEntity<?> getUserDetailsBySession(@RequestParam String sessionId) {
        try {
            User user = userService.getUserDetailsBySession(sessionId);

            return ResponseEntity.ok().body(user);


        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Internal Server Error: "));

        }
    }

}
