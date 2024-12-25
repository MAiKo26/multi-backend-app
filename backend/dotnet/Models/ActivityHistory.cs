using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json;

namespace dotnet.Models
{
    [Table("activity_history")]
    public class ActivityHistory
    {
        [Key]
        [Column("history_id")]
        public string Id { get; set; } = string.Empty;

        [Column("user_id")]
        public string UserId { get; set; } = string.Empty;

        [Column("description")]
        public string Description { get; set; } = string.Empty;

        [Column("done_at")]
        public DateTime DoneAt { get; set; } = DateTime.UtcNow;

        // Navigation property
        public User User { get; set; } = null!;

    }
}
