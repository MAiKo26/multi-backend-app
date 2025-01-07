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
    public IActionResult UpdateProfile([FromBody] UpdateProfileRequest request)
    {
        var updatedProfile = _profileService.UpdateProfile(request.Name, request.PhoneNumber, request.AvatarPath);
        return Ok(updatedProfile);
    }

    [HttpPut("password")]
    public IActionResult UpdatePassword([FromBody] UpdatePasswordRequest request)
    {
        _profileService.UpdatePassword(request.CurrentPassword, request.NewPassword);
        return Ok();
    }

    [HttpPut("settings")]
    public IActionResult UpdateNotificationSettings([FromBody] UserSetting newSetting)
    {
        _profileService.UpdateNotificationSettings(newSetting);
        return Ok();
    }
}

public class UpdateProfileRequest
{
    public string Name { get; set; }
    public string PhoneNumber { get; set; }
    public string AvatarPath { get; set; }
}

public class UpdatePasswordRequest
{
    public string CurrentPassword { get; set; }
    public string NewPassword { get; set; }
}