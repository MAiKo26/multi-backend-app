using dotnet.Interfaces;
using dotnet.Models;

namespace dotnet.Services;

public class ActivityHistoryService : IActivityHistoryService
{
    public IEnumerable<ActivityHistory> GetAllActivities()
    {
        throw new NotImplementedException();
    }

    public IEnumerable<ActivityHistory> GetActivityByEmail(string email)
    {
        throw new NotImplementedException();
    }
}