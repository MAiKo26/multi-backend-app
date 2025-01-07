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
    public IActionResult GetAllProjects([FromBody] string teamId)
    {
        var userProject = _projectService.GetAllProjects(teamId);

        return Ok(userProject);
    }

    [HttpPost]
    public IActionResult CreateProject([FromBody] CreateProjectRequest request)
    {
        _projectService.CreateProject(request.Name, request.TeamId);

        return Ok();
    }

    [HttpPost("/members")]
    public IActionResult AddMembersToProject([FromBody] AddMemberToProjectRequest request)
    {
        _projectService.AddMemberToProject(request.ProjectId, request.Email);

        return Ok();
    }

    [HttpDelete("{id}")]
    public IActionResult DeleteProject([FromQuery] string id)
    {
        _projectService.DeleteProject(id);

        return Ok();
    }
}

public class CreateProjectRequest
{
    public string Name { get; set; } 
    public string TeamId { get; set; } 
}

public class AddMemberToProjectRequest
{
    public string ProjectId { get; set; } 
    public string Email { get; set; } 
}