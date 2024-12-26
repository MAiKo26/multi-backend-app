package tn.maiko26.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.models.Task;
import tn.maiko26.springboot.models.Task;
import tn.maiko26.springboot.models.relations.TaskComment;
import tn.maiko26.springboot.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;


    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            return ResponseEntity.ok().body(tasks);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            taskService.createTask(task);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/:taskId")
    public ResponseEntity<?> updateTask(@RequestBody Task newTask,@RequestParam String taskId) {
        try {
            taskService.updateTask(newTask,taskId);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @DeleteMapping("/:taskId")
    public ResponseEntity<?> deleteTask(@RequestParam String taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PostMapping("/:taskId/comments")
    public ResponseEntity<?> addCommentToTask(@RequestBody TaskComment taskComment) {
        try {
            taskService.addCommentToTask(taskComment);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PostMapping("/:taskId/star")
    public ResponseEntity<?> starringTask(@RequestParam String taskId) {
        try {
            taskService.starringTask(taskId);
            return ResponseEntity.ok().body("Successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
