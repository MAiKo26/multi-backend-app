using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("project_members")]
    public class ProjectMember
    {
        [Key, Column("project_id")]
        public string ProjectId { get; set; } = string.Empty;

        [Key, Column("email")]
        public string Email { get; set; } = string.Empty;

        // Navigation properties
        public Project Project { get; set; } = null!;
        public User User { get; set; } = null!;
    }

}
