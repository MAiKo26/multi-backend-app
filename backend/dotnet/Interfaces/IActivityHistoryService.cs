using dotnet.Models;

namespace dotnet.Interfaces;

public interface IActivityHistoryService
{
    IEnumerable<ActivityHistory> GetAllActivities();

    IEnumerable<ActivityHistory> GetActivityByEmail(string email);
}