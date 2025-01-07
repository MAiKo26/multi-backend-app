using dotnet.Interfaces;
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
    public IActionResult UpdateTask([FromBody] UpdateTaskRequest request)
    {
        _taskService.UpdateTask(request.Name, request.Description, request.Finished, request.taskId);

        return Ok();
    }


    [HttpDelete("{taskId}")]
    public IActionResult DeleteTask([FromQuery] string taskId)
    {
        _taskService.DeleteTask(taskId);

        return Ok();
    }

    [HttpPost(":taskId/comments")]
    public IActionResult AddCommentToTask([FromBody] AddCommentToTaskRequest request)
    {
        _taskService.AddCommentToTask(request.taskId, request.comment);

        return Ok();
    }

    [HttpPost(":taskId/star")]
    public IActionResult StarringTask([FromBody] StarTaskRequest request)
    {
        _taskService.StarringTask(request.taskId, request.email);

        return Ok();
    }
}

public class UpdateTaskRequest
{
    public string taskId { get; set; }
    public string Name { get; set; }
    public string Description { get; set; }
    public bool Finished { get; set; }
}

public class AddCommentToTaskRequest
{
    public string taskId { get; set; }
    public string comment { get; set; }
}

public class StarTaskRequest
{
    public string taskId { get; set; }
    public string email { get; set; }
}