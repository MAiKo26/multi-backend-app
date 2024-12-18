using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers
{
    [ApiController]
    public class UserController : ControllerBase
    {
        // private readonly IUserService _userService;
        //
        // public UserController(IUserService userService)
        // {
        //     _userService = userService;
        // }

        [HttpGet]
        [Route("users")]
        public IActionResult GetUsers()
        {
            // var users = _userService.GetAllUsers();
            // return Ok(users);

            return Ok(new
                {
                    message = "Sucessfull"
                }
            );
        }
    }
}