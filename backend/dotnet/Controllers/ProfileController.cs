using dotnet.Interfaces;
using dotnet.Models;
using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers;

[ApiController]
[Route("profile")] // Added 'api' prefix for consistency
public class ProfileController : ControllerBase
{
    private readonly IProfileService _profileService;

    public ProfileController(IProfileService profileService)
    {
        _profileService = profileService;
    }

    [HttpGet] // Changed from "/" to use the route prefix
    public IActionResult GetUserProfile()
    {
        var userProfile = _profileService.GetCurrentUser();
        return Ok(userProfile);
    }

    [HttpPut] // Changed from "/" to use the route prefix
    public IActionResult UpdateProfile([FromBody] string name, string phoneNumber, string avatarPath)
    {
        var updatedProfile = _profileService.UpdateProfile(name, phoneNumber, avatarPath);
        return Ok(updatedProfile);
    }

    [HttpPut("password")]
    public IActionResult UpdatePassword([FromBody] string currentPassword, string newPassword)
    {
        _profileService.UpdatePassword(currentPassword, newPassword);
        return Ok();
    }

    [HttpPut("settings")]
    public IActionResult UpdateNotificationSettings([FromBody] UserSetting newSetting)
    {
        _profileService.UpdateNotificationSettings(newSetting);
        return Ok();
    }
}