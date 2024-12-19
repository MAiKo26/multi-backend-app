using dotnet.Models;

namespace dotnet.Interfaces;

public interface IUserService
{
    IEnumerable<User> GetAllUsers();
}