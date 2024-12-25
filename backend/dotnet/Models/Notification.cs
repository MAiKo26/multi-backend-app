using System.ComponentModel;
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
        [DefaultValue(false)]
        public bool Read { get; set; } = false;

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        [Column("link")]
        public string? Link { get; set; }

        // Navigation property
        public ICollection<UserNotification> UserNotifications { get; set; } = new List<UserNotification>();
    }
}

