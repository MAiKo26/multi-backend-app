using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace dotnet.Models
{
    [Table("users")]
    [Index(nameof(VerificationToken), IsUnique = true)] // Add Index attribute at the class level
    public class User
    {
        [Key]
        [Required]
        [EmailAddress]
        [Column("email")]
        public string Email { get; set; } = string.Empty;

        [Required]
        [Column("password")]
        public string Password { get; set; } = string.Empty;

        [Column("reset_password_token")]
        public string? ResetPasswordToken { get; set; }

        [Column("reset_password_expiry")]
        public DateTime? ResetPasswordExpiry { get; set; }

        [Column("failed_login_attempts")]
        [DefaultValue(0)]
        public int FailedLoginAttempts { get; set; } = 0;

        [Column("account_locked_until")]
        public DateTime? AccountLockedUntil { get; set; }

        [Column("is_verified")]
        [DefaultValue(false)]
        public bool IsVerified { get; set; } = false;

        [Column("verification_token")]
        public string? VerificationToken { get; set; }

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        [Column("last_login")]
        public DateTime? LastLogin { get; set; }

        [Required]
        [Column("name")]
        public string Name { get; set; } = string.Empty;

        [Column("avatar_url")]
        public string? AvatarUrl { get; set; }

        [Column("phone_number")]
        public string? PhoneNumber { get; set; }

        [Column("role")]
        [DefaultValue("user")]
        public string Role { get; set; } = "user";

        [Column("subscription_status")]
        [DefaultValue("free")]
        public string SubscriptionStatus { get; set; } = "free";

        [Column("subscription_id")]
        public string? SubscriptionId { get; set; }

        [Column("customer_id")]
        public string? CustomerId { get; set; }

        [Column("mba_points")]
        [DefaultValue(0)]
        public int MbaPoints { get; set; } = 0;

        [Column("email_notifications")]
        [DefaultValue(true)]
        public bool EmailNotifications { get; set; } = true;

        [Column("push_notifications")]
        [DefaultValue(true)]
        public bool PushNotifications { get; set; } = true;

        // Navigation properties
        public ActivityHistory? ActivityHistory { get; set; }
        public UserSetting? UserSettings { get; set; }
        public ICollection<Session> Sessions { get; set; } = new List<Session>();
        public ICollection<TeamMember> TeamMembers { get; set; } = new List<TeamMember>();
        public ICollection<UserNotification> UserNotifications { get; set; } = new List<UserNotification>();
        public ICollection<TaskComment> TaskComments { get; set; } = new List<TaskComment>();
        public ICollection<StarredTask> StarredTasks { get; set; } = new List<StarredTask>();
    }
}