using System.ComponentModel;
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
        [Column("project_id")]
        public string ProjectId { get; set; } = string.Empty;

        [Column("stars")]
        [DefaultValue(0)]
        public int Stars { get; set; } = 0;

        [Column("finished")]
        [DefaultValue(false)]
        public bool Finished { get; set; } = false;

        [Column("finished_by")]
        public string? FinishedBy { get; set; }

        // Navigation properties
        public Project Project { get; set; } = null!;
        public User? FinishedByUser { get; set; }
        public ICollection<TaskComment> TaskComments { get; set; } = new List<TaskComment>();
        public ICollection<StarredTask> StarredTasks { get; set; } = new List<StarredTask>();
    }
}
