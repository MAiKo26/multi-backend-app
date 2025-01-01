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
    public IActionResult CreateProject([FromBody] string name, string teamId)
    {
        _projectService.CreateProject(name, teamId);

        return Ok();
    }

    [HttpPost("/members")]
    public IActionResult AddMembersToProject([FromBody] string projectId, string email)
    {
        _projectService.AddMemberToProject(projectId, email);

        return Ok();
    }

    [HttpDelete("{id}")]
    public IActionResult DeleteProject([FromQuery] string id)
    {
        _projectService.DeleteProject(id);

        return Ok();
    }
}