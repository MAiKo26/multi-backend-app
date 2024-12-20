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

        [Column("failed_login_attempts", TypeName = "int")]
        [DefaultValue(0)]
        public int FailedLoginAttempts { get; set; } = 0;

        [Column("account_locked_until")]
        public DateTime? AccountLockedUntil { get; set; }

        [Column("is_verified", TypeName = "bit")]
        [DefaultValue(false)]
        public bool IsVerified { get; set; } = false;

        [Column("verification_token")]
        public string? VerificationToken { get; set; }

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        [Column("last_login")]
        public DateTime? LastLogin { get; set; }

        [Column("name")]
        public string? Name { get; set; }

        [Column("avatar_url")]
        public string? AvatarUrl { get; set; }

        [Column("phone_number")]
        public string? PhoneNumber { get; set; }

        [Column("role")]
        public string Role { get; set; } = "user";

        // Navigation property for related sessions
        public ICollection<Session>? Sessions { get; set; }
    }
}