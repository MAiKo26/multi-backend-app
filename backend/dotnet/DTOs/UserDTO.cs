using dotnet.Models;

namespace dotnet.DTOs;

public class UserDTO
{
    public String Email { get; set; }
    public String Name { get; set; }
    public String? AvatarUrl { get; set; }
    public String PhoneNumber { get; set; }
    public string Role { get; set; }
    public DateTime? LastLogin { get; set; }
}