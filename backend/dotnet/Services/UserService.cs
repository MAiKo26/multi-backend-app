using System.Security.Claims;
using dotnet.Data;
using dotnet.DTOs;
using dotnet.exceptions;
using dotnet.Interfaces;
using dotnet.Models;
using Microsoft.EntityFrameworkCore;

namespace dotnet.Services
{
    public class UserService : IUserService
    {
        private readonly DataContext _context;
        //private readonly IHttpContextAccessor _httpContextAccessor;
        private readonly ITeamService _teamService;

        public UserService(DataContext context, ITeamService teamService)
        {
            _context = context;
            //_httpContextAccessor = httpContextAccessor;
            _teamService = teamService;
        }

        public IEnumerable<User> GetAllUsers()
        {
            return _context.Users.ToList();
        }

        public IEnumerable<TeamMember> GetAllUsersByTeam(string teamId)
        {
            var team = _teamService.getTeamById(teamId);
            return _context.TeamMembers.Where(tm => tm.Team == team).ToList();
        }

        public UserWithSessionDto GetUserDetailsBySession(string sessionId)
        {
            var userSession = _context.Sessions
                .Include(s => s.User)
                .FirstOrDefault(s => s.SessionId == sessionId) ?? 
                throw new CustomException("Session not found", 400);

            return new UserWithSessionDto
            {
                sessionId = sessionId,
                email = userSession.User.Email,
                name = userSession.User.Name,
                avatar = userSession.User.AvatarUrl,
                role = userSession.User.Role,  
                phoneNumber = userSession.User.PhoneNumber,
                lastLogin = userSession.User.LastLogin
            };
        }

        public string GetCurrentUserEmail()
        {
            return "example@example.com"; 
                //_httpContextAccessor.HttpContext?.User.FindFirstValue(ClaimTypes.Email) ??
                //throw new CustomException("User not authenticated", 401);
        }

        public User GetUserByEmail(string email)
        {
            return _context.Users.FirstOrDefault(u => u.Email == email) ?? 
                throw new CustomException("User doesn't exist", 400);
        }

        public User GetCurrentUser()
        {
            var email = GetCurrentUserEmail();
            return GetUserByEmail(email);
        }

    }


}