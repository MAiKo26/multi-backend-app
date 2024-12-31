package tn.maiko26.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.CustomException;
import tn.maiko26.springboot.model.Project;
import tn.maiko26.springboot.model.Team;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.relations.ProjectMember;
import tn.maiko26.springboot.repository.ProjectRepository;
import tn.maiko26.springboot.repository.ProjectMemberRepository;
import tn.maiko26.springboot.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private ActivityHistoryService activityHistoryService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public List<Project> getAllProjects(String teamId) {
        if (teamId == null || teamId.isBlank()) {
            throw new CustomException("No team provided",400);
        }

        return projectRepository.findByTeam(teamService.getTeamById(teamId));
    }

    public Project createProject(String name, String teamId) {
        if (name == null || teamId == null) {
            throw new CustomException("Invalid project data",400);
        }

        String currentUserEmail = userService.getCurrentUserEmail();


        Project newProject = new Project();
        Team team =  teamService.getTeamById(teamId);
        newProject.setName(name);
        newProject.setTeam(team);
        newProject.setCreatedAt(new Date());

        Project savedProject = projectRepository.save(newProject);

        activityHistoryService.logActivity(currentUserEmail,
                "Created new project: " + name + " in team " + teamId
        );

        return savedProject;
    }

    public Project getProjectById(String projectid){
        return projectRepository.findById(projectid).orElseThrow(()-> new CustomException("No Project Exists",400));
    }

    public void addMembersToProject(String projectId, String email) {
        if (projectId == null || email == null || email.isBlank()) {
            throw new CustomException("Invalid member data",400);
        }

        Optional<ProjectMember> existingMember = projectMemberRepository
                .findByProjectAndUser(this.getProjectById(projectId), userService.getUserByEmail(email));

        if (existingMember.isPresent()) {
            throw new CustomException("User already in project",400);
        }

        ProjectMember newMember = new ProjectMember();
        newMember.setProject(projectRepository.findById(projectId).orElseThrow(()->new CustomException("Project not found",400)));
        newMember.setUser(userRepository.findByEmail(email).orElseThrow(()->new CustomException("User not found",400)));

        projectMemberRepository.save(newMember);

        activityHistoryService.logActivity(email,
                "Added user " + email + " to project " + projectId
        );
    }

    public void deleteProject(String projectId) {
        if (projectId == null) {
            throw new CustomException("Project ID cannot be null",400);
        }

        String currentUserEmail = userService.getCurrentUserEmail();

        Optional<Project> project = projectRepository.findById(projectId);

        if (project.isEmpty()) {
            throw new CustomException("Project not found",400);
        }

        projectRepository.deleteById(projectId);
        activityHistoryService.logActivity(currentUserEmail,
                "Deleted project: " + project.get().getName()
        );
    }
}
