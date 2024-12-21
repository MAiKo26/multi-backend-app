using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("projects")]
    public class Project
    {
        [Key]
        [Required]
        [Column("project_id")]
        public string Id { get; set; } = string.Empty;

        [Required]
        [Column("name")]
        public string Name { get; set; } = string.Empty;

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        [Column("stars")]
        public int Stars { get; set; } = 0;

        [Required]
        [ForeignKey("Team")]
        [Column("team_id")]
        public string TeamId { get; set; } = string.Empty;
        public Team Team { get; set; }

        public ICollection<Task> Tasks { get; set; } = new List<Task>();
        public ICollection<ProjectMember> ProjectMembers { get; set; } = new List<ProjectMember>();
    }
}
