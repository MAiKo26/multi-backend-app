package tn.maiko26.springboot.services;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.models.Task;
import tn.maiko26.springboot.models.relations.TaskComment;

import java.util.List;

@Service
public class TaskService {

    public List<Task> getAllTasks(){
        throw new NotImplementedException();

    }

    public void createTask(Task task){
        throw new NotImplementedException();

    }

    public void updateTask(Task newTask,String taskId){
        throw new NotImplementedException();

    }

    public void deleteTask(String taskId){
        throw new NotImplementedException();

    }

    public void addCommentToTask(TaskComment taskComment){
        throw new NotImplementedException();

    }


    public void starringTask(String taskId){
        throw new NotImplementedException();

    }
}
