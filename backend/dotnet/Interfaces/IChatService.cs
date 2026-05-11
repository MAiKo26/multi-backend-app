using dotnet.Models;
using Task = System.Threading.Tasks.Task;

namespace dotnet.Interfaces;

public interface IChatService
{
    Task InitializeRoom(string roomId);
    Task SaveMessage(string roomId, ChatMessage message);
    IEnumerable<ChatMessage> GetHistoryMessages(string roomId);
}