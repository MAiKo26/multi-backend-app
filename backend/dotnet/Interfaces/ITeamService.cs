using dotnet.Models;

namespace dotnet.Interfaces;

public interface ITeamService
{
    IEnumerable<Team> GetAllTeams();
    Team getTeamById(String id);
    void CreateTeam(Team team);
    void UpdateTeam(string teamId, Team team);
    void DeleteTeam(string id);
    void AddMemberToTeam(string teamId, string email);
    IEnumerable<Team> GetTeamsByUser(string email);
}