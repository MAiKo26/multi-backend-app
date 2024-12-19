using Microsoft.EntityFrameworkCore;
using dotnet.Models;
namespace dotnet.Data;

public class DataContext : DbContext
{
    public DataContext(DbContextOptions<DataContext> options) : base(options)
    {

    }

    public DbSet<User> Users { get; set; }
    public DbSet<Session> Sessions { get; set; }

}