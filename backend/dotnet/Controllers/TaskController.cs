using dotnet.Interfaces;
using Microsoft.AspNetCore.Mvc;

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
    public IActionResult GetAllTasksForProject()
    {
        try
        {
            var allTasks = _taskService.GetAllTasksForProject();

            return Ok(allTasks);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    [HttpPost]
    public IActionResult CreateTask()
    {
        try
        {
            _taskService.CreateTask();

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    [HttpPut]
    public IActionResult UpdateTask([FromQuery] string taskId)
    {
        try
        {
            _taskService.UpdateTask(taskId);

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    [HttpDelete]
    public IActionResult DeleteTask([FromQuery] string taskId)
    {
        try
        {
            _taskService.DeleteTask(taskId);

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    [HttpPost(":taskId/comments")]
    public IActionResult AddCommentToTask([FromQuery] string taskId)
    {
        try
        {
            _taskService.AddCommentToTask(taskId);

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    [HttpPost(":taskId/star")]
    public IActionResult StarringTask([FromQuery] string taskId)
    {
        try
        {
            _taskService.StarringTask(taskId);

            return Ok();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

}