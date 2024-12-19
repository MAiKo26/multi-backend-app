using dotnet.Data;
using dotnet.Interfaces;
using dotnet.Models;
using Microsoft.EntityFrameworkCore;

namespace dotnet.Services
{
    public class UserService : IUserService
    {
        private readonly DataContext _context;


        public UserService(DataContext context)
        {
            _context = context;
        }


        public IEnumerable<User> GetAllUsers()
        {
            return _context.Users.ToList();
        }
    }


}