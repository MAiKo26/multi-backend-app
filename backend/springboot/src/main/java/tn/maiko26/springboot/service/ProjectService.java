package tn.maiko26.springboot.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.ResourceNotImplementedException;
import tn.maiko26.springboot.model.Project;

import java.util.List;

@Service
public class ProjectService  {

    public List<Project> getAllProjects(){
        throw new ResourceNotImplementedException();

    }

    public void createProject(Project project){
        throw new ResourceNotImplementedException();

    }

    public void addMembersToProject(String password){
        throw new ResourceNotImplementedException();

    }

    public void deleteProject(String projectId){
        throw new ResourceNotImplementedException();

    }
}
