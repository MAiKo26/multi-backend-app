package tn.maiko26.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.maiko26.springboot.dto.UserDto;
import tn.maiko26.springboot.dto.UserWithSessionDto;
import tn.maiko26.springboot.dto.mappers.UserMapper;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.relations.TeamMember;
import tn.maiko26.springboot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyRole('user','admin')")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users
                .stream()
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok().body(userDtos);


    }

    @GetMapping("/byteam/:teamId")
    public ResponseEntity<?> getAllUsersByTeam(@RequestParam String teamId) {

        List<TeamMember> users = userService.getAllUsersByTeam(teamId);

        return ResponseEntity.ok().body(users);


    }


    @GetMapping("/bysession/:session")
    public ResponseEntity<?> getUserDetailsBySession(@RequestParam String sessionId) {

        UserWithSessionDto user = userService.getUserDetailsBySession(sessionId);

        return ResponseEntity.ok().body(user);


    }

}
