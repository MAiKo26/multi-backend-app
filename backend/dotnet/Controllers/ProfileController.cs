using dotnet.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers;

[ApiController]
[Route("profile")]  // Added 'api' prefix for consistency
public class ProfileController : ControllerBase
{
    private readonly IProfileService _profileService;

    public ProfileController(IProfileService profileService)
    {
        _profileService = profileService;
    }

    [HttpGet]  // Changed from "/" to use the route prefix
    public IActionResult GetUserProfile()
    {
        
            var userProfile = _profileService.GetUserProfile();
            return Ok(userProfile);
        
    }

    [HttpPut]  // Changed from "/" to use the route prefix
    public IActionResult UpdateProfile()
    {
        
            var updatedProfile = _profileService.UpdateProfile();
            return Ok(updatedProfile);
        
    }

    [HttpPut("password")]
    public IActionResult UpdatePassword()
    {
        
            var updatedPassword = _profileService.UpdatePassword();
            return Ok(updatedPassword);
        
    }

    [HttpPut("settings")]
    public IActionResult UpdateNotificationSettings()
    {
        
            var updatedNotificationSettings = _profileService.UpdateNotificationSettings();
            return Ok(updatedNotificationSettings);
        
    }
}