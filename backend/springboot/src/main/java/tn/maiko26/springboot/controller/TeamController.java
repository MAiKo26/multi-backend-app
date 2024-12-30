package tn.maiko26.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.model.Team;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/teams")
@PreAuthorize("hasRole('admin')")
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping
    public ResponseEntity<?> getAllTeams() {

        List<Team> teams = teamService.getAllTeams();
        return ResponseEntity.ok().body(teams);


    }

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody Team team) {

        teamService.createTeam(team);
        return ResponseEntity.ok().body("Successful");


    }

    @PutMapping("/:teamId")
    public ResponseEntity<?> updateTeam(@RequestBody Team newTeam, @RequestParam String teamId) {

        teamService.updateTeam(teamId, newTeam);
        return ResponseEntity.ok().body("Successful");


    }

    @DeleteMapping("/:teamId")
    public ResponseEntity<?> deleteTeam(@RequestParam String teamId) {

        teamService.deleteTeam(teamId);
        return ResponseEntity.ok().body("Successful");


    }

    @PostMapping("/members")
    public ResponseEntity<?> addMemberToTeam(@RequestBody User user) {

        teamService.addMemberToTeam(user);
        return ResponseEntity.ok().body("Successful");


    }

    @GetMapping("/byuser/:email")
    @PreAuthorize("hasAnyRole('user','admin')")
    public ResponseEntity<?> getTeamsByUserEmail(@RequestParam String email) {

        teamService.getTeamsByUserEmail(email);
        return ResponseEntity.ok().body("Successful");


    }

}
