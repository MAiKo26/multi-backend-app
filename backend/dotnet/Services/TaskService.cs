using dotnet.Data;
using dotnet.exceptions;
using dotnet.Interfaces;
using dotnet.Models;
using Task = dotnet.Models.Task;

namespace dotnet.Services;

public class TaskService : ITaskService
{
    private readonly DataContext _context;
    private readonly IUserService _userService;

    public TaskService(DataContext context, IUserService userService)
    {
        _context = context;
        _userService = userService;
    }

    public IEnumerable<Task> GetAllTasks()
    {
        return _context.Tasks.ToList();
    }

    public IEnumerable<Task> GetAllTasksForProject(string projectId)
    {
        return _context.Tasks.Where(t => t.ProjectId == projectId).ToList();
    }

    public void CreateTask(Task task)
    {
        _context.Tasks.Add(task);
        _context.SaveChanges();
    }

    public void UpdateTask(string name, string description, bool finished, string taskId)
    {
        var task = _context.Tasks.FirstOrDefault(t => t.Id == taskId) ??
                   throw new CustomException("Task doesn't exist", 400);

        task.Name = name;
        task.Description = description;
        task.Finished = finished;
        task.FinishedBy = finished ? _userService.GetCurrentUser().Name : null;

        _context.Update(task);
        _context.SaveChanges();
    }

    public void DeleteTask(string id)
    {
        var task = _context.Tasks.FirstOrDefault(t => t.Id == id) ??
                   throw new CustomException("Task doesn't exist", 400);

        _context.Tasks.Remove(task);
        _context.SaveChanges();
    }

    public void AddCommentToTask(string taskId, string comment)
    {
        TaskComment taskComment = new TaskComment()
        {
            Task = _context.Tasks.Where(t => t.Id == taskId).FirstOrDefault(),
            Content = comment,
            CreatedAt = DateTime.Now,
            User = _userService.GetCurrentUser(),
            TaskId = taskId,
            UserId = _userService.GetCurrentUser().Email
        };
        _context.TaskComments.Add(taskComment);
        _context.SaveChanges();
    }

    public void StarringTask(string taskId, string userEmail)
    {
        var task = _context.Tasks.FirstOrDefault(t => t.Id == taskId) ??
                   throw new CustomException("Task doesn't exist", 400);

        var user = _userService.GetUserByEmail(userEmail);

        var existingStar = _context.StarredTasks
            .FirstOrDefault(st => st.Task == task && st.User == user);

        if (existingStar != null)
        {
            _context.StarredTasks.Remove(existingStar);
        }
        else
        {
            var starredTask = new StarredTask()
            {
                Task = task,
                User = user,
                CreatedAt = DateTime.Now
            };
            _context.StarredTasks.Add(starredTask);
        }

        _context.SaveChanges();
    }
}