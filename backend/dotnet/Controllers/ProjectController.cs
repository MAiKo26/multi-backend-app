using dotnet.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers;

[ApiController]
[Route("projects")]
public class ProjectController : ControllerBase
{
    private readonly IProjectService _projectService;

    public ProjectController(IProjectService projectService)
    {
        _projectService = projectService;
    }
    
    [HttpGet]
    public IActionResult GetAllProjects()
    {
        
            var userProject = _projectService.GetAllProjects();

            return Ok(userProject);
        
    }
    
    [HttpPost]
    public IActionResult CreateProject()
    {
        
             _projectService.CreateProject();

            return Ok();
        
    }

    [HttpPost("/members")]
    public IActionResult AddMembersToProject()
    {
        
            _projectService.AddMemberToProject();

            return Ok();
        
    }
    
    [HttpDelete("{id}")]
    public IActionResult DeleteProject()
    {
        
            _projectService.DeleteProject();

            return Ok();
        
    }


}