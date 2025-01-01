using dotnet.Data;
using dotnet.Interfaces;
using dotnet.Models;
using Microsoft.EntityFrameworkCore;

namespace dotnet.Services;

public class ActivityHistoryService : IActivityHistoryService
{
    private readonly DataContext _context;
    private readonly UserService _userService; 

    public ActivityHistoryService(DataContext context, UserService userService)
    {
        _context = context;
        _userService = userService;
    }
   
    public IEnumerable<ActivityHistory> GetAllActivities()
    {
        return _context.ActivityHistories
            .Include(a => a.User)
            .OrderByDescending(a => a.DoneAt)
            .ToList();
    }

    public IEnumerable<ActivityHistory> GetActivityByEmail(string email)
    {
        if (string.IsNullOrWhiteSpace(email))
        {
            throw new ArgumentException("Email cannot be null or empty");
        }

        return _context.ActivityHistories
            .Include(a => a.User)
            .Where(a => a.User.Email == email)
            .OrderByDescending(a => a.DoneAt)
            .ToList();
    }

    public void LogActivity(string userEmail, string description)
    {
        try
        {
            var user = _userService.GetUserByEmail(userEmail);
           
            var activityHistory = new ActivityHistory
            {
                Id = Guid.NewGuid().ToString(),
                User = user,
                Description = description,
                DoneAt = DateTime.Now
            };

            _context.ActivityHistories.Add(activityHistory);
            _context.SaveChanges();
        }
        catch (Exception e)
        {
            Console.Error.WriteLine($"Failed to log activity: {e.Message}");
            throw;
        }
    }
}