using dotnet.Interfaces;
using Microsoft.Extensions.DependencyInjection;
using System.Net.WebSockets;
using System.Text;
using Task = System.Threading.Tasks.Task;

namespace dotnet.Middlewares;

public class WebSocketMiddleware
{
    private readonly RequestDelegate _next;
    private readonly IServiceScopeFactory _scopeFactory;
    private static readonly Dictionary<string, HashSet<WebSocket>> _rooms = new();

    public WebSocketMiddleware(RequestDelegate next, IServiceScopeFactory scopeFactory)
    {
        _next = next;
        _scopeFactory = scopeFactory;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        if (context.Request.Path.StartsWithSegments("/ws"))
        {
            if (context.WebSockets.IsWebSocketRequest)
            {
                var pathSegments = context.Request.Path.Value?.Split('/');
                var roomId = pathSegments?.Length > 2 ? pathSegments[2] : "general";
                if (string.IsNullOrEmpty(roomId))
                    roomId = "general";

                var webSocket = await context.WebSockets.AcceptWebSocketAsync();
                await HandleWebSocket(webSocket, roomId);
            }
            else
            {
                context.Response.StatusCode = 400;
            }
        }
        else
        {
            await _next(context);
        }
    }

    private async Task HandleWebSocket(WebSocket webSocket, string roomId)
    {
        if (!_rooms.ContainsKey(roomId))
        {
            _rooms[roomId] = new HashSet<WebSocket>();
        }
        _rooms[roomId].Add(webSocket);

        try
        {
            using var scope = _scopeFactory.CreateScope();
            var chatService = scope.ServiceProvider.GetRequiredService<IChatService>();
            await chatService.InitializeRoom(roomId);

            while (webSocket.State == WebSocketState.Open)
            {
                var buffer = new byte[4096];
                var result = await webSocket.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);

                if (result.MessageType == WebSocketMessageType.Close)
                {
                    await webSocket.CloseAsync(WebSocketCloseStatus.NormalClosure, "Closing", CancellationToken.None);
                    break;
                }

                if (result.MessageType == WebSocketMessageType.Text)
                {
                    var message = Encoding.UTF8.GetString(buffer, 0, result.Count);
                    await ProcessMessage(message, roomId, webSocket);
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"WebSocket error: {ex.Message}");
        }
        finally
        {
            _rooms[roomId].Remove(webSocket);
            if (_rooms[roomId].Count == 0)
            {
                _rooms.Remove(roomId);
            }
        }
    }

    private async Task ProcessMessage(string messageJson, string roomId, WebSocket sender)
    {
        try
        {
            var message = System.Text.Json.JsonSerializer.Deserialize<ChatMessageDto>(messageJson);
            if (message == null) return;

            using var scope = _scopeFactory.CreateScope();
            var chatService = scope.ServiceProvider.GetRequiredService<IChatService>();

            await chatService.SaveMessage(roomId, new dotnet.Models.ChatMessage
            {
                Id = Guid.NewGuid().ToString(),
                RoomId = roomId,
                SenderId = message.senderId,
                Content = message.content,
                CreatedAt = DateTime.UtcNow,
                ReadBy = new List<string>()
            });

            var messageBytes = Encoding.UTF8.GetBytes(messageJson);
            var segment = new ArraySegment<byte>(messageBytes);

            if (_rooms.ContainsKey(roomId))
            {
                foreach (var client in _rooms[roomId])
                {
                    if (client != sender && client.State == WebSocketState.Open)
                    {
                        await client.SendAsync(segment, WebSocketMessageType.Text, true, CancellationToken.None);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error processing message: {ex.Message}");
        }
    }
}

public class ChatMessageDto
{
    public string roomId { get; set; } = string.Empty;
    public int id { get; set; }
    public List<string> readBy { get; set; } = new();
    public string senderId { get; set; } = string.Empty;
    public string createdAt { get; set; } = string.Empty;
    public string content { get; set; } = string.Empty;
}