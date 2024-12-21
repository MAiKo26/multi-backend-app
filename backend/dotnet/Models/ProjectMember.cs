using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("project_members")]
    public class ProjectMember
    {
        [Key]
        [Required]
        [Column("project_member_id")]
        public string Id { get; set; } = string.Empty;

        [Required]
        [ForeignKey("Project")]
        [Column("project_id")]
        public string ProjectId { get; set; } = string.Empty;
        public Project Project { get; set; }

        [Required]
        [ForeignKey("User")]
        [Column("email")]
        public string Email { get; set; } = string.Empty;
        public User User { get; set; }
    }
}
