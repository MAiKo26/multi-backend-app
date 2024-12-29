package tn.maiko26.springboot.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.ResourceNotImplementedException;
import tn.maiko26.springboot.model.Task;
import tn.maiko26.springboot.model.relations.TaskComment;

import java.util.List;

@Service
public class TaskService {

    public List<Task> getAllTasks(){
        throw new ResourceNotImplementedException();

    }

    public void createTask(Task task){
        throw new ResourceNotImplementedException();

    }

    public void updateTask(Task newTask,String taskId){
        throw new ResourceNotImplementedException();

    }

    public void deleteTask(String taskId){
        throw new ResourceNotImplementedException();

    }

    public void addCommentToTask(TaskComment taskComment){
        throw new ResourceNotImplementedException();

    }


    public void starringTask(String taskId){
        throw new ResourceNotImplementedException();

    }
}
