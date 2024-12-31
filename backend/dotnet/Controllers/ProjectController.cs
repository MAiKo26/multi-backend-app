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
        try
        {
            var userProject = _projectService.GetAllProjects();

            return Ok(userProject);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    [HttpPost]
    public IActionResult CreateProject()
    {
        try
        {
             _projectService.CreateProject();

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    [HttpPost("/members")]
    public IActionResult AddMembersToProject()
    {
        try
        {
            _projectService.AddMemberToProject();

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    [HttpDelete("{id}")]
    public IActionResult DeleteProject()
    {
        try
        {
            _projectService.DeleteProject();

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }


}