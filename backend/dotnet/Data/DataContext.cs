using dotnet.Models;
using Microsoft.EntityFrameworkCore;
using Task = dotnet.Models.Task;

namespace dotnet.Data
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {
        }

        public DbSet<ActivityHistory> ActivityHistories { get; set; }
        public DbSet<Notification> Notifications { get; set; }
        public DbSet<Project> Projects { get; set; }
        public DbSet<Session> Sessions { get; set; }
        public DbSet<Task> Tasks { get; set; }
        public DbSet<Team> Teams { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<UserSetting> UserSettings { get; set; }
        public DbSet<ProjectMember> ProjectMembers { get; set; }
        public DbSet<StarredTask> StarredTasks { get; set; }
        public DbSet<TaskComment> TaskComments { get; set; }
        public DbSet<TeamMember> TeamMembers { get; set; }
        public DbSet<UserNotification> UserNotifications { get; set; }


        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // Many To Many
            modelBuilder.Entity<TeamMember>()
                .HasKey(tm => new { tm.TeamId, tm.Email });

            modelBuilder.Entity<ProjectMember>()
                .HasKey(pm => new { pm.ProjectId, pm.Email });

            modelBuilder.Entity<UserNotification>()
                .HasKey(un => new { un.UserId, un.NotificationId });

            modelBuilder.Entity<StarredTask>()
                .HasKey(st => new { st.UserId, st.TaskId });

            //  Cascading deletes
            modelBuilder.Entity<Session>()
                .HasOne(s => s.User)
                .WithMany(u => u.Sessions)
                .HasForeignKey(s => s.Email)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<UserSetting>()
                .HasOne(us => us.User)
                .WithOne(u => u.UserSettings)
                .HasForeignKey<UserSetting>(us => us.UserEmail)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<ActivityHistory>()
                .HasOne(ah => ah.User)
                .WithOne(u => u.ActivityHistory)
                .HasForeignKey<ActivityHistory>(ah => ah.UserId)
                .OnDelete(DeleteBehavior.Cascade);

            // Cascading no action
            modelBuilder.Entity<Task>()
                .HasOne(t => t.FinishedByUser)
                .WithMany()
                .HasForeignKey(t => t.FinishedBy)
                .OnDelete(DeleteBehavior.NoAction);
        }
    }
}