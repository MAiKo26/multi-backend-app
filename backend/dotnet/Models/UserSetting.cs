using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace dotnet.Models
{
    [Table("user_settings")]
    public class UserSetting
    {
        [Key]
        [Column("user_email")]
        public string UserEmail { get; set; } = string.Empty;

        [Column("email_digest")]
        [DefaultValue(true)]
        public bool EmailDigest { get; set; } = true;

        [Column("task_reminders")]
        [DefaultValue(true)]
        public bool TaskReminders { get; set; } = true;

        // Navigation property
        [JsonIgnore]
        public User User { get; set; } = null!;
    }
}
