using dotnet.Data;
using dotnet.exceptions;
using dotnet.Interfaces;
using dotnet.Models;
using Microsoft.AspNetCore.Authorization;

namespace dotnet.Services;

[Authorize(Roles = "admin")]
public class TeamService : ITeamService
{
    private readonly DataContext _context;


    public TeamService(DataContext context)
    {
        _context = context;

    }

    public IEnumerable<Team> GetAllTeams()
    {
        return _context.Teams.ToList();
    }

    public Team getTeamById(String id)
    {
        return _context.Teams.FirstOrDefault(t => t.Id == id) ?? 
               throw new CustomException($"No Team Found With Id: {id}", 400);

    }

    public void CreateTeam(Team team)
    {
        Team newTeam = new Team()
        {
            Id = team.Id,
            Name = team.Name,
            CreatedAt = new DateTime()
        };

        _context.Add(newTeam);

        var saved = _context.SaveChanges();

        if (saved < 0)
        {
            throw new CustomException("Couldn't Create the team", 400);
        }
    }

    public void UpdateTeam(string teamId, Team team)
    {
        var updatedTeam = this.getTeamById(teamId) ?? 
                          throw new CustomException($"No Team Found With Id: {teamId}", 400);
        
        updatedTeam.Name = team.Name;
        updatedTeam.TeamMembers = team.TeamMembers;
        updatedTeam.Projects = team.Projects;

        _context.Update(updatedTeam);
    
        var saved = _context.SaveChanges();
        if (saved < 0)
        {
            throw new CustomException("Error updating the team", 400);
        }
    }

    public void DeleteTeam(string id)
    {
        var team = this.getTeamById(id) ?? 
                   throw new CustomException($"No Team Found With Id: {id}", 400);
        
        _context.Remove(team);
        _context.SaveChanges();
    }

    public void AddMemberToTeam(string teamId, string email)
    {
        var team = this.getTeamById(teamId) ?? 
                   throw new CustomException($"No Team Found With Id: {teamId}", 400);
        
        var user = _context.Users.FirstOrDefault(u => u.Email == email) ?? 
                   throw new CustomException($"No User Found With Email: {email}", 400);

        var teamMember = new TeamMember
        {
            Team = team,
            User = user
        };

        _context.TeamMembers.Add(teamMember);
        _context.SaveChanges();
    }

    [Authorize(Roles = "admin,user")]
    public IEnumerable<Team> GetTeamsByUser(string email)
    {
        return _context.Teams
            .Where(t => t.TeamMembers.Any(m => m.User.Email == email))
            .ToList();
    }
}