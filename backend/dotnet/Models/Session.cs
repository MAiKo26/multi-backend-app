using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("sessions")]
    public class Session
    {
        [Key]
        [Required]
        [Column("session_id")]
        public required string SessionId { get; set; } = string.Empty;

        [Required]
        [ForeignKey("User")]
        [Column("email")]
        public required string Email { get; set; } = string.Empty;

        public User User { get; set; }

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        [Column("expires_at")]
        public DateTime ExpiresAt { get; set; } = DateTime.UtcNow.AddHours(1);
    }
}