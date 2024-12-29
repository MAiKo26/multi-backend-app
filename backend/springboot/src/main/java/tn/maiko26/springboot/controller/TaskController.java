package tn.maiko26.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.model.Task;
import tn.maiko26.springboot.model.relations.TaskComment;
import tn.maiko26.springboot.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;


    @GetMapping
    public ResponseEntity<?> getAllTasks() {

        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok().body(tasks);


    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {

        taskService.createTask(task);
        return ResponseEntity.ok().body("Successful");


    }

    @PutMapping("/:taskId")
    public ResponseEntity<?> updateTask(@RequestBody Task newTask, @RequestParam String taskId) {

        taskService.updateTask(newTask, taskId);
        return ResponseEntity.ok().body("Successful");


    }

    @DeleteMapping("/:taskId")
    public ResponseEntity<?> deleteTask(@RequestParam String taskId) {

        taskService.deleteTask(taskId);
        return ResponseEntity.ok().body("Successful");


    }

    @PostMapping("/:taskId/comments")
    public ResponseEntity<?> addCommentToTask(@RequestBody TaskComment taskComment) {

        taskService.addCommentToTask(taskComment);
        return ResponseEntity.ok().body("Successful");


    }

    @PostMapping("/:taskId/star")
    public ResponseEntity<?> starringTask(@RequestParam String taskId) {

        taskService.starringTask(taskId);
        return ResponseEntity.ok().body("Successful");


    }
}
