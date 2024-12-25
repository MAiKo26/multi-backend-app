using dotnet.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers;

[ApiController]
[Route("profile")]
public class ProfileController : ControllerBase
{
    private readonly IProfileService _profileService;

    public ProfileController(IProfileService profileService)
    {
        _profileService = profileService;
    }

    [HttpGet]
    public IActionResult GetUserProfile()
    {
        try
        {
            var userProfile = _profileService.GetUserProfile();

            return Ok(userProfile);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    [HttpPut]
    public IActionResult UpdateProfile()
    {
        try
        {
            var updatedProfile = _profileService.UpdateProfile();

            return Ok(updatedProfile);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    [HttpPut("password")]
    public IActionResult UpdatePassword()
    {
        try
        {
            var updatedPassword = _profileService.UpdatePassword();

            return Ok(updatedPassword);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    [HttpPut("settings")]
    public IActionResult UpdateNotificationSettings()
    {
        try
        {
            var updatedNotificationSettings = _profileService.UpdateNotificationSettings();

            return Ok(updatedNotificationSettings);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
}