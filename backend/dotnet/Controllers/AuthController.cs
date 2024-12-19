using dotnet.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers
{
    [ApiController]
    [Route("auth")]
    public class AuthController : ControllerBase
    {
        private readonly IAuthService _authService;

        public AuthController(IAuthService authService)
        {
            _authService = authService;
        }

        // Logging in

        [HttpPost("login")]
        public IActionResult Login([FromBody] AuthRequestData request)
        {
            try
            {
                string token = _authService.Login(request.email, request.password);
                return Ok(new AuthResponseData { token = token });
            }
            catch (Exception ex)
            {
                return Unauthorized(new { message = ex.Message });
            }
        }

        [HttpPost("logout")]
        public IActionResult Logout([FromBody] LogoutRequestData payload)
        {
            string SessionId = payload.verificationToken;

            _authService.Logout(SessionId);
            return Ok("Logged out successfully");
        }

        // Registering

        [HttpPost("register")]
        public IActionResult Register([FromBody] AuthRequestData payload)
        {
            try
            {
                _authService.Register(payload.email, payload.password);
                return Ok("Email Sent Successfully");
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return BadRequest(e.Message);
            }
        }

        [HttpPost("register/verification")]
        public IActionResult RegisterVerification([FromBody] LogoutRequestData payload)
        {
            try
            {
                string VerificationToken = payload.verificationToken;

                _authService.RegisterVerification(VerificationToken);
                return Ok("Verification successful");
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return BadRequest(e.Message);
            }
        }

        // Password Reset

        [HttpPost("password-reset")]
        public IActionResult PasswordReset([FromBody] PasswordResetRequestData payload)
        {
            try
            {
                _authService.PasswordReset(payload.email);
                return Ok("Password reset link sent to email");
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return BadRequest(e.Message);
            }
        }

        [HttpPost("password-reset/verification")]
        public IActionResult PasswordResetVerification([FromBody] PasswordResetVerificationRequestData payload)
        {
            try
            {
                _authService.PasswordResetVerification(payload.resetPasswordToken);
                return Ok("Valid token");
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return BadRequest(e.Message);
            }
        }

        [HttpPost("password-reset/confirmation")]
        public IActionResult PasswordResetConfirmation([FromBody] PasswordResetConfirmationRequestData payload)
        {
            try
            {
                _authService.PasswordResetConfirmation(payload.resetPasswordToken, payload.newPassword);
                return Ok("Password reset successful");
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return BadRequest(e.Message);
            }
        }
    }

    // DTOs
    public class AuthRequestData
    {
        public string email { get; set; }
        public string password { get; set; }
    }

    public class AuthResponseData
    {
        public string token { get; set; }
    }

    public class LogoutRequestData
    {
        public string verificationToken { get; set; }
    }

    public class PasswordResetRequestData
    {
        public string email { get; set; }
    }

    public class PasswordResetVerificationRequestData
    {
        public string resetPasswordToken { get; set; }
    }

    public class PasswordResetConfirmationRequestData
    {
        public string resetPasswordToken { get; set; }
        public string newPassword { get; set; }
    }
}