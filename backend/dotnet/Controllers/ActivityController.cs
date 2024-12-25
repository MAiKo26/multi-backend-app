using dotnet.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers;

[ApiController]
[Route("activity")]
public class ActivityController : ControllerBase
{
    private readonly IActivityHistoryService _activityHistoryService;

    public ActivityController(IActivityHistoryService activityHistoryService)
    {
        _activityHistoryService = activityHistoryService;
    }

    [HttpGet]
    public IActionResult GetAllActivities()
    {
        try
        {
            var allActivities = _activityHistoryService.GetAllActivities();

            return Ok(allActivities);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    [HttpGet("user")]
    public IActionResult GetActivityByEmail([FromQuery] string email)
    {
        try
        {
            if (string.IsNullOrEmpty(email))
            {
                return BadRequest("Email parameter is required.");
            }

            var userActivityByEmail = _activityHistoryService.GetActivityByEmail(email);

            return Ok(userActivityByEmail);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
}