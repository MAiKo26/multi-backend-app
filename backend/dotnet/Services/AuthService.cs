using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using dotnet.Data;
using dotnet.Interfaces;
using dotnet.Models;
using dotnet.Utils;
using Microsoft.IdentityModel.Tokens;

namespace dotnet.Services;

public class AuthService : IAuthService
{
    private readonly DataContext _context;
    private readonly IEmailSenderService _emailSenderService;
    private readonly ILogger<AuthService> _logger;
    private readonly JwtSecurityTokenHandler _tokenHandler;

    public AuthService(
        DataContext context,
        IEmailSenderService emailSenderService,
        ILogger<AuthService> logger)
    {
        _context = context;
        _emailSenderService = emailSenderService;
        _logger = logger;
        _tokenHandler = new JwtSecurityTokenHandler();
    }

    public string Login(string email, string password)
    {
        var user = _context.Users.SingleOrDefault(u => u.Email == email);
        if (user == null) throw new ArgumentException("Invalid email");

        if (user.AccountLockedUntil.HasValue && user.AccountLockedUntil > DateTime.UtcNow)
            throw new InvalidOperationException("Account is locked. Try again later.");

        if (!VerifyPassword(password, user.Password))
        {
            HandleFailedLogin(user);
            throw new ArgumentException("Invalid password");
        }

        if (!user.IsVerified) throw new InvalidOperationException("Email not verified.");

        var token = GenerateJwtToken(user.Email);
        SaveSession(user, token);

        ResetLoginAttempts(user);
        return token;
    }

    public void Logout(string token)
    {
        var session = _context.Sessions.SingleOrDefault(s => s.SessionId == token);
        if (session != null) _context.Sessions.Remove(session);
        _context.SaveChanges();
    }

    public async void Register(string email, string password)
    {
        if (_context.Users.Any(u => u.Email == email))
            throw new ArgumentException("Email already exists");

        var hashedPassword = HashPassword(password);
        var verificationToken = TokenUtils.GenerateRandomToken();

        var user = new User
        {
            Email = email,
            Password = hashedPassword,
            VerificationToken = verificationToken,
            IsVerified = false,
            CreatedAt = DateTime.UtcNow
        };

        _context.Users.Add(user);
        _context.SaveChanges();

        await _emailSenderService.SendVerificationEmailAsync(email, verificationToken);
    }

    public void RegisterVerification(string verificationToken)
    {
        var user = _context.Users.SingleOrDefault(u => u.VerificationToken == verificationToken);
        if (user == null) throw new ArgumentException("Invalid verification token");

        user.IsVerified = true;
        user.VerificationToken = null;

        _context.SaveChanges();
    }

    public async void PasswordReset(string email)
    {
        var user = _context.Users.SingleOrDefault(u => u.Email == email);
        if (user == null) throw new ArgumentException("Email not found");

        var resetToken = TokenUtils.GenerateRandomToken();
        user.ResetPasswordToken = resetToken;
        user.ResetPasswordExpiry = DateTime.UtcNow.AddHours(1);

        _context.SaveChanges();
        await _emailSenderService.SendResetEmailAsync(email, resetToken);
    }

    public void PasswordResetVerification(string resetPasswordToken)
    {
        var user = _context.Users.SingleOrDefault(u =>
            u.ResetPasswordToken == resetPasswordToken &&
            u.ResetPasswordExpiry > DateTime.UtcNow);

        if (user == null) throw new ArgumentException("Invalid or expired reset token");

        user.ResetPasswordToken = null;
        user.ResetPasswordExpiry = null;

        _context.SaveChanges();
    }

    public void PasswordResetConfirmation(string resetPasswordToken, string newPassword)
    {
        var user = _context.Users.SingleOrDefault(u => u.ResetPasswordToken == resetPasswordToken);
        if (user == null) throw new ArgumentException("Invalid reset token");

        user.Password = HashPassword(newPassword);
        user.ResetPasswordToken = null;
        user.ResetPasswordExpiry = null;

        _context.SaveChanges();
    }

    private void HandleFailedLogin(User user)
    {
        user.FailedLoginAttempts++;
        if (user.FailedLoginAttempts >= 5)
        {
            user.AccountLockedUntil = DateTime.UtcNow.AddMinutes(15);
            user.FailedLoginAttempts = 0;
        }

        _context.SaveChanges();
    }

    private void ResetLoginAttempts(User user)
    {
        user.FailedLoginAttempts = 0;
        user.LastLogin = DateTime.UtcNow;
        _context.SaveChanges();
    }

    private string GenerateJwtToken(string email)
    {
        // TODO
        var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("MyVeryVeryReallyKindaNotReallyJustLetMeDoASecretKeyPleAse"));
        var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
        var token = new JwtSecurityToken(
            claims: new[] { new Claim(JwtRegisteredClaimNames.Email, email) },
            expires: DateTime.UtcNow.AddHours(1),
            signingCredentials: creds);

        return _tokenHandler.WriteToken(token);
    }

    private bool VerifyPassword(string password, string hashedPassword)
    {
        // TODO
        return true;
    }

    private string HashPassword(string password)
    {
        // TODO
        return password;
    }

    private void SaveSession(User user, string token)
    {
        var session = new Session
        {
            SessionId = token,
            Email = user.Email,
            User = user,
            CreatedAt = DateTime.UtcNow,
            ExpiresAt = DateTime.UtcNow.AddHours(1)
        };

        _context.Sessions.Add(session);
        _context.SaveChanges();
    }
}