using dotnet.Data;
using dotnet.exceptions;
using dotnet.Interfaces;
using dotnet.Models;

namespace dotnet.Services;

public class ProjectService : IProjectService
{
    
    private readonly DataContext _context;
   private readonly TeamService _teamService;
   private readonly UserService _userService;
   private readonly ActivityHistoryService _activityService;

   public ProjectService(DataContext context, TeamService teamService, 
       UserService userService, ActivityHistoryService activityService)
   {
       _context = context;
       _teamService = teamService;
       _userService = userService;
       _activityService = activityService;
   }
   
   public IEnumerable<Project> GetAllProjects(string teamId)
   {
       if (string.IsNullOrWhiteSpace(teamId))
           throw new CustomException("No team provided", 400);

       var team = _teamService.getTeamById(teamId);
       return _context.Projects.Where(p => p.Team == team).ToList();
   }

   public void CreateProject(string name, string teamId)
   {
       if (string.IsNullOrWhiteSpace(name) || string.IsNullOrWhiteSpace(teamId))
           throw new CustomException("Invalid project data", 400);

       var currentUserEmail = _userService.GetCurrentUserEmail();
       var team = _teamService.getTeamById(teamId);
       
       var newProject = new Project
       {
           Name = name,
           Team = team,
           CreatedAt = DateTime.Now
       };

       _context.Projects.Add(newProject);
       _context.SaveChanges();

       _activityService.LogActivity(currentUserEmail, 
           $"Created new project: {name} in team {teamId}");

   }

   public void AddMemberToProject(string projectId, string email)
   {
       if (string.IsNullOrWhiteSpace(projectId) || string.IsNullOrWhiteSpace(email))
           throw new CustomException("Invalid member data", 400);

       var project = GetProjectById(projectId);
       var user = _userService.GetUserByEmail(email);

       var existingMember = _context.ProjectMembers
           .FirstOrDefault(pm => pm.Project == project && pm.User == user);

       if (existingMember != null)
           throw new CustomException("User already in project", 400);

       var newMember = new ProjectMember
       {
           Project = project,
           User = user
       };

       _context.ProjectMembers.Add(newMember);
       _context.SaveChanges();

       _activityService.LogActivity(email, 
           $"Added user {email} to project {projectId}");
   }

   public Project GetProjectById(string projectId)
   {
       return _context.Projects.FirstOrDefault(p => p.Id == projectId) ??
           throw new CustomException("No Project Exists", 400);
   }

   public void DeleteProject(string projectId)
   {
       if (string.IsNullOrWhiteSpace(projectId))
           throw new CustomException("Project ID cannot be null", 400);

       var currentUserEmail = _userService.GetCurrentUserEmail();
       var project = GetProjectById(projectId);

       _context.Projects.Remove(project);
       _context.SaveChanges();

       _activityService.LogActivity(currentUserEmail,
           $"Deleted project: {project.Name}");
   }
}