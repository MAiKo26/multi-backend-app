using dotnet.Models;

namespace dotnet.Interfaces;

public interface ITeamService
{
    IEnumerable<Team> GetAllTeams();
    void CreateTeam();
    void UpdateTeam(string id);
    void DeleteTeam(string id);
    void AddMemberToTeam();
    IEnumerable<Team> GetTeamsByUser();
}