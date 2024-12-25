using dotnet.Services;

namespace dotnet.Interfaces;

public interface IProfileService
{
    ProfileService GetUserProfile();

    ProfileService UpdateProfile();
    
    ProfileService UpdatePassword();
    
    ProfileService UpdateNotificationSettings();
}
