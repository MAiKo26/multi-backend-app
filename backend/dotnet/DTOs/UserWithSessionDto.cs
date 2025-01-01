namespace dotnet.DTOs;

public class UserWithSessionDto
{
    public string sessionId { get; set; }
    public string email { get; set; }
    public string name { get; set; }
    public string avatar { get; set; }
    public string role { get; set; }
    public string phoneNumber { get; set; }
    public DateTime? lastLogin { get; set; }
    
    public UserWithSessionDto(){}

    public UserWithSessionDto(string sessionId, string email, string name, string avatar, string role, string phoneNumber, DateTime lastLogin)
    {
        this.sessionId = sessionId;
        this.email = email;
        this.name = name;
        this.avatar = avatar;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.lastLogin = lastLogin;
    }
}