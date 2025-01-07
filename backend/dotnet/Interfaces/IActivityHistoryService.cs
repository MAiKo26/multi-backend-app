using dotnet.Models;

namespace dotnet.Interfaces;

public interface IActivityHistoryService
{
    IEnumerable<ActivityHistory> GetAllActivities();

    IEnumerable<ActivityHistory> GetActivityByEmail(string email);
    
    void LogActivity(string userEmail, string description);
}