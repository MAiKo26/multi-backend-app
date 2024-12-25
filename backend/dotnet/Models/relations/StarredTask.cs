using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("starred_tasks")]
    public class StarredTask
    {
        [Key, Column("user_id")]
        public string UserId { get; set; } = string.Empty;

        [Key, Column("task_id")]
        public string TaskId { get; set; } = string.Empty;

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        // Navigation properties
        public User User { get; set; } = null!;
        public Task Task { get; set; } = null!;

    }


}
