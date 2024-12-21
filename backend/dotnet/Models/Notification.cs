using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("notifications")]
    public class Notification
    {
        [Key]
        [Required]
        [Column("notification_id")]
        public string Id { get; set; } = string.Empty;

        [ForeignKey("User")]
        [Column("user_id")]
        public string UserId { get; set; } = string.Empty;
        public User User { get; set; }

        [Required]
        [Column("type")]
        public string Type { get; set; } = string.Empty;

        [Required]
        [Column("title")]
        public string Title { get; set; } = string.Empty;

        [Required]
        [Column("content")]
        public string Content { get; set; } = string.Empty;

        [Column("read")]
        public bool Read { get; set; } = false;

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        [Column("link")]
        public string? Link { get; set; }
    }
}

