using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("task_comments")]
    public class TaskComment
    {
        [Key]
        [Required]
        [Column("comment_id")]
        public string Id { get; set; } = string.Empty;

        [Required]
        [Column("task_id")]
        public string TaskId { get; set; } = string.Empty;

        [Required]
        [Column("user_id")]
        public string UserId { get; set; } = string.Empty;

        [Required]
        [Column("content")]
        [MaxLength(255)]
        public string Content { get; set; } = string.Empty;

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        // Navigation properties
        public Task Task { get; set; } = null!;
        public User User { get; set; } = null!;
    }
}