using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers
{
    [ApiController]
    [Route("auth")]
    public class AuthController : ControllerBase
    {
        // private readonly IAuthService _authService;
        //
        // public AuthController(IAuthService authService)
        // {
        //     _authService = authService;
        // }

        // Logging in

        [HttpPost("login")]
        public IActionResult Login([FromBody] LoginRequestData request)
        {
            try
            {
                string token = "_authService.Login(request.Email, request.Password)";
                return Ok(new LoginResponseData { Token = token });
            }
            catch (Exception ex)
            {
                return Unauthorized(new { message = ex.Message });
            }
        }

        [HttpPost("logout")]
        public IActionResult Logout([FromBody] LogoutRequestData payload)
        {
            string sessionId = payload.VerificationToken;

            // _authService.Logout(sessionId);
            return Ok("Logged out successfully");
        }

        // Registering

        [HttpPost("register")]
        public IActionResult Register([FromBody] LogoutRequestData payload)
        {
            string sessionId = payload.VerificationToken;

            // _authService.Logout(sessionId);
            return Ok("Logged out successfully");
        }

        [HttpPost("register/verification")]
        public IActionResult RegisterVerification([FromBody] LogoutRequestData payload)
        {
            string sessionId = payload.VerificationToken;

            // _authService.Logout(sessionId);
            return Ok("Logged out successfully");
        }

        // Password Reset

        [HttpPost("password-reset")]
        public IActionResult PasswordReset([FromBody] LogoutRequestData payload)
        {
            string sessionId = payload.VerificationToken;

            // _authService.Logout(sessionId);
            return Ok("Logged out successfully");
        }

        [HttpPost("password-reset/verification")]
        public IActionResult PasswordResetVerification([FromBody] LogoutRequestData payload)
        {
            string sessionId = payload.VerificationToken;

            // _authService.Logout(sessionId);
            return Ok("Logged out successfully");
        }

        [HttpPost("password-reset/confirmation")]
        public IActionResult PasswordResetConfirmation([FromBody] LogoutRequestData payload)
        {
            string sessionId = payload.VerificationToken;

            // _authService.Logout(sessionId);
            return Ok("Logged out successfully");
        }
    }

    // DTOs
    public class LoginRequestData
    {
        public string Email { get; set; }
        public string Password { get; set; }
    }

    public class LoginResponseData
    {
        public string Token { get; set; }
    }

    public class LogoutRequestData
    {
        public string VerificationToken { get; set; }
    }
}