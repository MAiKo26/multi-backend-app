using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace dotnet.Models
{
    [Table("teams")]
    public class Team
    {
        [Key]
        [Column("team_id")]
        public String Id { get; set; }

        [Required]
        [Column("name")]
        public string Name { get; set; } = string.Empty;

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        // Navigation properties
        [JsonIgnore]
        public ICollection<TeamMember> TeamMembers { get; set; } = new List<TeamMember>();
        [JsonIgnore]
        public ICollection<Project> Projects { get; set; } = new List<Project>();
    }
}