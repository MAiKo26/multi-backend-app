package tn.maiko26.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.CustomException;
import tn.maiko26.springboot.exception.ResourceNotImplementedException;
import tn.maiko26.springboot.model.Task;
import tn.maiko26.springboot.model.relations.StarredTask;
import tn.maiko26.springboot.model.relations.TaskComment;
import tn.maiko26.springboot.repository.StarredTaskRepository;
import tn.maiko26.springboot.repository.TaskCommentRepository;
import tn.maiko26.springboot.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskCommentRepository taskCommentRepository;

    @Autowired
    private StarredTaskRepository starredTaskRepository;
    @Autowired
    private UserService userService;

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Assuming you have a repository for Task entity
    }

    // Get tasks by project
    public List<Task> getTasksForProject(String projectId) {
        return taskRepository.findByProjectId(projectId); // Query to get tasks by projectId
    }

    // Create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task); // Save the task entity
    }

    // Update an existing task
    public Task updateTask(String taskId, Task newTask) {
        Optional<Task> existingTask = taskRepository.findById(taskId);
        if (existingTask.isPresent()) {
            Task updatedTask = existingTask.get();
            updatedTask.setName(newTask.getName());
            updatedTask.setDescription(newTask.getDescription());
            updatedTask.setFinished(newTask.getFinished());
            updatedTask.setFinishedBy(newTask.getFinishedBy());
            return taskRepository.save(updatedTask); // Save the updated task entity
        }
        throw new ResourceNotImplementedException(); // Throw exception if task doesn't exist
    }

    // Delete task by id
    public void deleteTask(String taskId) {
        taskRepository.deleteById(taskId); // Delete the task by ID
    }

    // Add comment to a task
    public TaskComment addCommentToTask(TaskComment taskComment) {
        return taskCommentRepository.save(taskComment); // Save the task comment entity
    }

    // Star or unstar a task
    public void starringTask(String taskId, String userEmail) {
        Optional<StarredTask> existingStar = starredTaskRepository.findByTaskIdAndUserId(taskId, userEmail);

        if (existingStar.isPresent()) {
            starredTaskRepository.delete(existingStar.get()); // If already starred, unstar the task
        } else {
            StarredTask starredTask = new StarredTask();
            starredTask.setTask(taskRepository.findById(taskId).orElseThrow(() -> new CustomException("Task not found.", 400)));
            starredTask.setUser(userService.getUserByEmail(userEmail));
            starredTask.setCreatedAt(new Date());
            starredTaskRepository.save(starredTask); // Otherwise, star the task
        }
    }
}
