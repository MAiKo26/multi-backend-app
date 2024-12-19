namespace dotnet.Interfaces;

public interface IEmailSenderService
{
    Task SendVerificationEmailAsync(string email, string verificationToken);
    Task SendResetEmailAsync(string email, string resetToken);
}