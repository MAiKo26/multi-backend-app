namespace dotnet.Models
{
    public class Session
    {
        private  string SessionId { get; set; }

        // reference users.email
        private  string Email { get; set; }
        private DateOnly CreatedAt { get; set; }
        private DateOnly ExpiresAt { get; set; }
    }
}