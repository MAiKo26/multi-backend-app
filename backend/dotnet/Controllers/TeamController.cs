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
    public IActionResult UpdateTeam([FromBody] UpdateTeamRequest request)
    {
        _teamService.UpdateTeam(request.teamId, request.team);

        return Ok();
    }

    [HttpDelete("{id}")]
    public IActionResult DeleteTeam([FromQuery] string id)
    {
        _teamService.DeleteTeam(id);

        return Ok();
    }

    [HttpPost("members")]
    public IActionResult AddMemberToTeam([FromBody] AddMemberToTeamRequest request )
    {
        _teamService.AddMemberToTeam(request.teamId, request.email);

        return Ok();
    }

    [HttpGet("/teamsbyuser/{email}")]
    public IActionResult GetTeamsByUser([FromQuery] string email)
    {
        _teamService.GetTeamsByUser(email);

        return Ok();
    }
}

public class AddMemberToTeamRequest
{
    public string teamId { get; set; }
    public string email { get; set; }
}

public class UpdateTeamRequest
{
    public string teamId { get; set; }
    public Team team { get; set; }
}