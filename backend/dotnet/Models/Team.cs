using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("teams")]
    public class Team
    {
        [Key]
        [Required]
        [Column("team_id")]
        public string Id { get; set; } = string.Empty;

        [Required]
        [Column("name")]
        public string Name { get; set; } = string.Empty;

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        public ICollection<TeamMember> TeamMembers { get; set; } = new List<TeamMember>();
        public ICollection<Project> Projects { get; set; } = new List<Project>();
    }
}