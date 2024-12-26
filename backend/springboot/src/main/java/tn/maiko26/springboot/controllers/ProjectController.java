package tn.maiko26.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.models.Project;
import tn.maiko26.springboot.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        try {
            List<Project> projects = projectService.getAllProjects();
            return ResponseEntity.ok().body(projects);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            projectService.createProject(project);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PostMapping("/members")
    public ResponseEntity<?> addMembersToProject(@RequestBody String password) {
        try {
            projectService.addMembersToProject(password);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @DeleteMapping("/:projectId")
    public ResponseEntity<?> deleteProject(@RequestBody String projectId) {
        try {
            projectService.deleteProject(projectId);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

}
