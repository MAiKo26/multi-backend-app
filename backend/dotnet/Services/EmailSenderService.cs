using dotnet.Configuration;
using dotnet.Interfaces;
using MailKit.Net.Smtp;
using MailKit.Security;
using Microsoft.Extensions.Options;
using MimeKit;

namespace dotnet.Services;

public class EmailSenderService : IEmailSenderService
{
    private readonly EmailConfig _emailConfig;

    public EmailSenderService(IOptions<EmailConfig> emailConfig)
    {
        _emailConfig = emailConfig.Value;
    }

    public async Task SendVerificationEmailAsync(string email, string verificationToken)
    {
        var message = new MimeMessage();
        message.From.Add(new MailboxAddress(_emailConfig.FromName, _emailConfig.FromEmail));
        message.To.Add(new MailboxAddress("", email));
        message.Subject = "Verify your email";

        var bodyBuilder = new BodyBuilder
        {
            TextBody = $"Click here to verify: http://localhost:5173/auth/email-confirmation/{verificationToken}",
            HtmlBody = $@"
                <h3>Email Verification</h3>
                <p>Please click the link below to verify your email:</p>
                <p><a href='http://localhost:5173/auth/email-confirmation/{verificationToken}'>Verify Email</a></p>"
        };

        message.Body = bodyBuilder.ToMessageBody();

        await SendEmailAsync(message);
    }

    public async Task SendResetEmailAsync(string email, string resetToken)
    {
        var message = new MimeMessage();
        message.From.Add(new MailboxAddress(_emailConfig.FromName, _emailConfig.FromEmail));
        message.To.Add(new MailboxAddress("", email));
        message.Subject = "Password Reset Code";

        var bodyBuilder = new BodyBuilder
        {
            TextBody = $"Your password reset code is: http://localhost:5173/auth/password-reset/{resetToken}",
            HtmlBody = $@"
                <h3>Password Reset</h3>
                <p>Click the link below to reset your password:</p>
                <p><a href='http://localhost:5173/auth/password-reset/{resetToken}'>Reset Password</a></p>"
        };

        message.Body = bodyBuilder.ToMessageBody();

        await SendEmailAsync(message);
    }

    private async Task SendEmailAsync(MimeMessage message)
    {
        using var client = new SmtpClient();

        await client.ConnectAsync(_emailConfig.SmtpServer, _emailConfig.SmtpPort, SecureSocketOptions.StartTls);
        await client.AuthenticateAsync(_emailConfig.SmtpUsername, _emailConfig.SmtpPassword);
        await client.SendAsync(message);
        await client.DisconnectAsync(true);
    }
}