using dotnet.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers;

[ApiController]
[Route("chat")]
public class ChatController : ControllerBase
{
    private readonly IChatService _chatService;

    public ChatController(IChatService chatService)
    {
        _chatService = chatService;
    }

    [HttpGet("{roomId}")]
    public IActionResult GetHistoryMessages(string roomId)
    {
        var messages = _chatService.GetHistoryMessages(roomId);
        return Ok(messages);
    }
}