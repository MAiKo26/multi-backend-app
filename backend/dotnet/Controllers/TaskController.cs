using dotnet.Interfaces;
using dotnet.Models;
using Microsoft.AspNetCore.Mvc;
using Task = dotnet.Models.Task;

namespace dotnet.Controllers;

[ApiController]
[Route("tasks")]
public class TaskController : ControllerBase
{
    private readonly ITaskService _taskService;

    public TaskController(ITaskService taskService)
    {
        _taskService = taskService;
    }

    [HttpGet("project")]
    public IActionResult GetAllTasksForProject([FromQuery] string projectId)
    {
        var allTasks = _taskService.GetAllTasksForProject(projectId);

        return Ok(allTasks);
    }

    [HttpPost]
    public IActionResult CreateTask([FromBody] Task task)
    {
        _taskService.CreateTask(task);

        return Ok();
    }

    [HttpPut]
    public IActionResult UpdateTask([FromBody] string taskId,Task task)
    {
        _taskService.UpdateTask(task.Name,task.Description,task.Finished,taskId);

        return Ok();
    }

    [HttpDelete("{taskId}")]
    public IActionResult DeleteTask([FromQuery] string taskId)
    {
        _taskService.DeleteTask(taskId);

        return Ok();
    }

    [HttpPost(":taskId/comments")]
    public IActionResult AddCommentToTask([FromBody] string taskId, string comment)
    {

        _taskService.AddCommentToTask(taskId,comment);

        return Ok();
    }

    [HttpPost(":taskId/star")]
    public IActionResult StarringTask([FromBody] string taskId,string email)
    {
        _taskService.StarringTask(taskId,email);

        return Ok();
    }
}