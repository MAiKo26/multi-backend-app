using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json;

namespace dotnet.Models
{
    [Table("activity_history")]
    public class ActivityHistory
    {
        [Key]
        [Required]
        [Column("history_id")]
        public string Id { get; set; } = string.Empty;

        [ForeignKey("User")]
        [Column("user_id")]
        public string UserId { get; set; } = string.Empty;
        public User User { get; set; }

        [Required]
        [Column("type")]
        public string Type { get; set; } = string.Empty;

        [Required]
        [Column("action")]
        public string Action { get; set; } = string.Empty;

        [Column("resource_id")]
        public string? ResourceId { get; set; }

        [Column("resource_type")]
        public string? ResourceType { get; set; }

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        [Column("metadata")]
        public JsonDocument Metadata { get; set; } = JsonDocument.Parse("{}");
    }
}
