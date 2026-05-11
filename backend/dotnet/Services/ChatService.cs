using dotnet.Data;
using dotnet.Interfaces;
using dotnet.Models;
using Microsoft.EntityFrameworkCore;
using Task = System.Threading.Tasks.Task;

namespace dotnet.Services;

public class ChatService : IChatService
{
    private readonly DataContext _context;

    public ChatService(DataContext context)
    {
        _context = context;
    }

    public async Task InitializeRoom(string roomId)
    {
        var existingRoom = await _context.ChatRooms.FirstOrDefaultAsync(r => r.Id == roomId);
        if (existingRoom == null)
        {
            var newRoom = new ChatRoom
            {
                Id = roomId,
                Name = roomId == "general" ? "General Chat" : $"Private Room {roomId}",
                Type = roomId == "general" ? "general" : "private",
                CreatedAt = DateTime.UtcNow
            };
            _context.ChatRooms.Add(newRoom);
            await _context.SaveChangesAsync();
        }
    }

    public async Task SaveMessage(string roomId, ChatMessage message)
    {
        var room = await _context.ChatRooms.FirstOrDefaultAsync(r => r.Id == roomId);
        if (room == null)
        {
            await InitializeRoom(roomId);
        }

        var newMessage = new ChatMessage
        {
            Id = Guid.NewGuid().ToString(),
            RoomId = roomId,
            SenderId = message.SenderId,
            Content = message.Content,
            CreatedAt = DateTime.UtcNow,
            ReadBy = new List<string>()
        };

        _context.ChatMessages.Add(newMessage);
        await _context.SaveChangesAsync();
    }

    public IEnumerable<ChatMessage> GetHistoryMessages(string roomId)
    {
        return _context.ChatMessages
            .Where(m => m.RoomId == roomId)
            .OrderBy(m => m.CreatedAt)
            .ToList();
    }
}