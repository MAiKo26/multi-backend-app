namespace dotnet.Interfaces;

public interface IAuthService
{
    string Login(string email, string password);
    void Logout(string token);
    void Register(string email, string password);
    void RegisterVerification(string verificationToken);
    void PasswordReset(string email);
    void PasswordResetVerification(string resetPasswordToken);
    void PasswordResetConfirmation(string resetPasswordToken, string newPassword);
}