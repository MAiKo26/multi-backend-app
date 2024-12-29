package tn.maiko26.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.model.Project;
import tn.maiko26.springboot.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getAllProjects() {

        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok().body(projects);


    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {

        projectService.createProject(project);
        return ResponseEntity.ok().body("Successful");


    }

    @PostMapping("/members")
    public ResponseEntity<?> addMembersToProject(@RequestBody String password) {

        projectService.addMembersToProject(password);
        return ResponseEntity.ok().body("Successful");


    }

    @DeleteMapping("/:projectId")
    public ResponseEntity<?> deleteProject(@RequestBody String projectId) {

        projectService.deleteProject(projectId);
        return ResponseEntity.ok().body("Successful");


    }

}
