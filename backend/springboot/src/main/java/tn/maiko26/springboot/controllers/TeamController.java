package tn.maiko26.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.models.Team;
import tn.maiko26.springboot.models.User;
import tn.maiko26.springboot.services.TeamService;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    TeamService teamService;


    @GetMapping
    public ResponseEntity<?> getAllTeams() {
        try {
            List<Team> teams = teamService.getAllTeams();
            return ResponseEntity.ok().body(teams);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody Team team) {
        try {
            teamService.createTeam(team);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/:teamId")
    public ResponseEntity<?> updateTeam(@RequestBody Team newTeam, @RequestParam String teamId) {
        try {
            teamService.updateTeam(teamId, newTeam);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @DeleteMapping("/:teamId")
    public ResponseEntity<?> deleteTeam(@RequestParam String teamId) {
        try {
            teamService.deleteTeam(teamId);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PostMapping("/members")
    public ResponseEntity<?> addMemberToTeam(@RequestBody User user) {
        try {
            teamService.addMemberToTeam(user);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/byuser/:email")
    public ResponseEntity<?> getTeamsByUserEmail(@RequestParam String email) {
        try {
            teamService.getTeamsByUserEmail(email);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

}
