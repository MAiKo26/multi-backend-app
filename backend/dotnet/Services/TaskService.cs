using dotnet.Interfaces;
using Task = dotnet.Models.Task;

namespace dotnet.Services;

public class TaskService : ITaskService
{
    public IEnumerable<Task> GetAllTasksForProject()
    {
        throw new NotImplementedException();
    }

    public void CreateTask()
    {
        throw new NotImplementedException();
    }

    public void UpdateTask(string id)
    {
        throw new NotImplementedException();
    }

    public void DeleteTask(string id)
    {
        throw new NotImplementedException();
    }

    public void AddCommentToTask(string id)
    {
        throw new NotImplementedException();
    }

    public void StarringTask(string id)
    {
        throw new NotImplementedException();
    }
}