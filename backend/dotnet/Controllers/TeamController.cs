using dotnet.Interfaces;
using dotnet.Models;
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
    public IActionResult CreateTeam([FromBody] Team team)
    {
        _teamService.CreateTeam(team);

        return Ok();
    }

    [HttpPut]
    public IActionResult UpdateTeam([FromBody] string teamId, Team team)
    {
        _teamService.UpdateTeam(teamId, team);

        return Ok();
    }

    [HttpDelete("{id}")]
    public IActionResult DeleteTeam([FromQuery] string id)
    {
        _teamService.DeleteTeam(id);

        return Ok();
    }

    [HttpPost("members")]
    public IActionResult AddMemberToTeam([FromBody] string teamId, string email)
    {
        _teamService.AddMemberToTeam(teamId, email);

        return Ok();
    }

    [HttpGet("/teamsbyuser/{email}")]
    public IActionResult GetTeamsByUser([FromQuery] string email)
    {
        _teamService.GetTeamsByUser(email);

        return Ok();
    }
}