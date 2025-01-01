using dotnet.Models;
using dotnet.Services;

namespace dotnet.Interfaces;

public interface IProfileService
{
    User GetCurrentUser();

    User UpdateProfile(string name, string phoneNumber, string avatarPath);

    void UpdatePassword(string currentPassword, string newPassword);

    void UpdateNotificationSettings(UserSetting newSetting);
    
    
    
}
