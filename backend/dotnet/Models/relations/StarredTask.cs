using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

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
        [JsonIgnore]
        public User User { get; set; } = null!;
        [JsonIgnore]
        public Task Task { get; set; } = null!;

    }


}
