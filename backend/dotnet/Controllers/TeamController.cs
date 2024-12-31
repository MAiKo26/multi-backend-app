using dotnet.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers;

[ApiController]
[Route("teams")]
public class TeamController : ControllerBase
{
    private readonly ITeamService _teamService;

    public TeamController(ITeamService teamService)
    {
        _teamService = teamService;
    }
    
    [HttpGet]
    public IActionResult GetAllTeams()
    {
        try
        {
            var allTeams = _teamService.GetAllTeams();

            return Ok(allTeams);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    [HttpPost]
    public IActionResult CreateTeam()
    {
        try
        {
            _teamService.CreateTeam();

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    [HttpPut]
    public IActionResult UpdateTeam([FromQuery] string id)
    {
        try
        {
            _teamService.UpdateTeam(id);

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    [HttpDelete("{id}")]
    public IActionResult DeleteTeam([FromQuery] string id)
    {
        try
        {
            _teamService.DeleteTeam(id);

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    [HttpPost("members")]
    public IActionResult AddMemberToTeam()
    {
        try
        {
            _teamService.AddMemberToTeam();

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    [HttpGet("/teamsbyuser")]
    public IActionResult GetTeamsByUser()
    {
        try
        {
            _teamService.GetTeamsByUser();

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

}