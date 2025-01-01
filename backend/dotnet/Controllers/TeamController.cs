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
        
            var allTeams = _teamService.GetAllTeams();

            return Ok(allTeams);
        
    }
    
    [HttpPost]
    public IActionResult CreateTeam()
    {
        
            _teamService.CreateTeam();

            return Ok();
        
    }

    [HttpPut]
    public IActionResult UpdateTeam([FromQuery] string id)
    {
        
            _teamService.UpdateTeam(id);

            return Ok();
        
    }
    
    [HttpDelete("{id}")]
    public IActionResult DeleteTeam([FromQuery] string id)
    {
        
            _teamService.DeleteTeam(id);

            return Ok();
        
    }
    
    [HttpPost("members")]
    public IActionResult AddMemberToTeam()
    {
        
            _teamService.AddMemberToTeam();

            return Ok();
        
    }
    
    [HttpGet("/teamsbyuser")]
    public IActionResult GetTeamsByUser()
    {
        
            _teamService.GetTeamsByUser();

            return Ok();
        
    }

}