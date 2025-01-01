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
        
            var allActivities = _activityHistoryService.GetAllActivities();

            return Ok(allActivities);
        
    }

    [HttpGet("user")]
    public IActionResult GetActivityByEmail([FromQuery] string email)
    {
        
            if (string.IsNullOrEmpty(email))
            {
                return BadRequest("Email parameter is required.");
            }

            var userActivityByEmail = _activityHistoryService.GetActivityByEmail(email);

            return Ok(userActivityByEmail);
        
    }
}