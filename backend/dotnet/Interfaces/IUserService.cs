using dotnet.DTOs;
using dotnet.Models;

namespace dotnet.Interfaces;

public interface IUserService
{
    IEnumerable<User> GetAllUsers();
    User GetCurrentUser();
    User GetUserByEmail(string email);

    string GetCurrentUserEmail();

    UserWithSessionDto GetUserDetailsBySession(string sessionId);

    IEnumerable<TeamMember> GetAllUsersByTeam(string teamId);

}