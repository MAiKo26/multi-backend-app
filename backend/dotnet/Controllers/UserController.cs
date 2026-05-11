using AutoMapper;
using dotnet.DTOs;
using dotnet.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers
{
    [ApiController]
    [Route("users")]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;

        private readonly IMapper _mapper;

        public UserController(IUserService userService, IMapper mapper)
        {
            _userService = userService;
            _mapper = mapper;
        }

        [HttpGet]
        public IActionResult GetUsers()
        {
            var users = _userService.GetAllUsers();
            var usersDTO = _mapper.Map<IEnumerable<UserDTO>>(users); 
            return Ok(usersDTO);
        }

        [HttpGet("team/{teamId}")]
        public IActionResult GetUsersByTeam(string teamId)
        {
            var teamMembers = _userService.GetAllUsersByTeam(teamId);
            return Ok(teamMembers);
        }

    [HttpGet("session/{sessionId}")]
    public IActionResult GetUserBySession(string sessionId)
    {
        var user = _userService.GetUserDetailsBySession(sessionId);
        return Ok(user);
    }

    [HttpGet("bysession/{sessionId}")]
    public IActionResult GetUserBySessionAlt(string sessionId)
    {
        var user = _userService.GetUserDetailsBySession(sessionId);
        return Ok(user);
    }

    [HttpGet("online")]
        public IActionResult GetOnlineUsers()
        {
            var onlineUsers = _userService.GetOnlineUsers();
            return Ok(onlineUsers.Select(email => new { email }));
        }
    }
}