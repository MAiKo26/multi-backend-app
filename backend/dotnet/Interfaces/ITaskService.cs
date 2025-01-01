using dotnet.Models;
using Task = dotnet.Models.Task;

namespace dotnet.Interfaces;

public interface ITaskService
{
    IEnumerable<Task> GetAllTasksForProject(string projectId);
    void CreateTask(Task task);
    void UpdateTask(string name, string description, bool finished, string taskId);
    void DeleteTask(string id);
    void AddCommentToTask(string taskId,string comment);
    void StarringTask(string taskId, string userEmail);
}