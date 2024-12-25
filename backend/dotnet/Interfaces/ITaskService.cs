using Task = dotnet.Models.Task;

namespace dotnet.Interfaces;

public interface ITaskService
{
    IEnumerable<Task> GetAllTasksForProject();
    void CreateTask();
    void UpdateTask(string id);
    void DeleteTask(string id);
    void AddCommentToTask(string id);
    void StarringTask(string id);
}