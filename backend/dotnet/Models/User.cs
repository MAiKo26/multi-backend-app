namespace dotnet.Models
{
    public class User
    {
        private string Email { get; set; }
        private string Password { get; set; }

        // Auth
        private string ResetPasswordToken { get; set; }
        private DateOnly ResetPasswordExpiry { get; set; }
        private int FailedLoginAttempts { get; set; }
        private DateOnly AccountLockedUntil { get; set; }
        private bool IsVerified { get; set; }
        private string VerificationToken { get; set; }
        private DateOnly CreatedAt { get; set; }
        private DateOnly LastLogin { get; set; }

        // Profile
        private string Name { get; set; }
        private string AvatarUrl { get; set; }
        private string PhoneNumber { get; set; }
        private string Role { get; set; }

        public User(string email, string password)
        {
            this.Email = email;
            this.Password = password;
        }

        public User()
        {
        }
    }
}