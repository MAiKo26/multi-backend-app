using dotnet.Data;
using dotnet.Interfaces;
using dotnet.Models;

namespace dotnet.Services;

public class ActivityHistoryService : IActivityHistoryService
{
    
    private readonly DataContext _context;


    public ActivityHistoryService(DataContext context)
    {
        _context = context;
    }
    
    public IEnumerable<ActivityHistory> GetAllActivities()
    {
        throw new NotImplementedException();
    }

    public IEnumerable<ActivityHistory> GetActivityByEmail(string email)
    {
        throw new NotImplementedException();
    }

    public void LogActivity(string userId, string description)
    {
        throw new NotImplementedException();
    }
}