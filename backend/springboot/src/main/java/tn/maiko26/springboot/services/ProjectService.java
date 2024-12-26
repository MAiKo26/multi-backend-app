package tn.maiko26.springboot.services;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.models.Project;

import java.util.List;

@Service
public class ProjectService  {

    public List<Project> getAllProjects(){
        throw new NotImplementedException();

    }

    public void createProject(Project project){
        throw new NotImplementedException();

    }

    public void addMembersToProject(String password){
        throw new NotImplementedException();

    }

    public void deleteProject(String projectId){
        throw new NotImplementedException();

    }
}
