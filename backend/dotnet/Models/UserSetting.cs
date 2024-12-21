using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("user_settings")]
    public class UserSetting
    {
        [Key]
        [ForeignKey("User")]
        [Column("user_id")]
        public string UserId { get; set; } = string.Empty;
        public User User { get; set; }

        [Column("email_digest")]
        public bool EmailDigest { get; set; } = true;

        [Column("task_reminders")]
        public bool TaskReminders { get; set; } = true;
    }
}
