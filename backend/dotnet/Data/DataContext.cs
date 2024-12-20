using Microsoft.EntityFrameworkCore;
using dotnet.Models;

namespace dotnet.Data
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Session> Sessions { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // Configure cascading delete behavior
            modelBuilder.Entity<Session>()
                .HasOne(s => s.User) // Navigation property in Session
                .WithMany(u => u.Sessions) // Navigation property in User
                .HasForeignKey(s => s.Email) // Foreign key in Session
                .OnDelete(DeleteBehavior.Cascade); // Cascade delete

            base.OnModelCreating(modelBuilder);
        }
    }
}