package tn.maiko26.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.model.Project;
import tn.maiko26.springboot.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
@PreAuthorize("hasAnyRole('user','admin')")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getAllProjects(@RequestParam String teamId) {

        List<Project> projects = projectService.getAllProjects(teamId);
        return ResponseEntity.ok().body(projects);


    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody String name, String teamId) {

        projectService.createProject(name,teamId);
        return ResponseEntity.ok().body("Successful");


    }

    @PostMapping("/members")
    public ResponseEntity<?> addMembersToProject(@RequestBody String projectId, String email) {

        projectService.addMembersToProject(projectId,email);
        return ResponseEntity.ok().body("Successful");


    }

    @DeleteMapping("/:projectId")
    public ResponseEntity<?> deleteProject(@RequestBody String projectId) {

        projectService.deleteProject(projectId);
        return ResponseEntity.ok().body("Successful");


    }

}
