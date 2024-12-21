using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("tasks")]
    public class Task
    {
        [Key]
        [Required]
        [Column("task_id")]
        public string Id { get; set; } = string.Empty;

        [Required]
        [Column("name")]
        public string Name { get; set; } = string.Empty;

        [Required]
        [Column("description")]
        public string Description { get; set; } = string.Empty;

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        [Required]
        [ForeignKey("Project")]
        [Column("project_id")]
        public string ProjectId { get; set; } = string.Empty;
        public Project Project { get; set; }

        [Required]
        [ForeignKey("User")]
        [Column("assignee")]
        public string AssigneeId { get; set; } = string.Empty;
        public User Assignee { get; set; }

        [Required]
        [Column("status")]
        public string Status { get; set; } = string.Empty;

        [Required]
        [Column("priority")]
        public string Priority { get; set; } = string.Empty;

        public ICollection<TaskComment> Comments { get; set; } = new List<TaskComment>();
    }
}
