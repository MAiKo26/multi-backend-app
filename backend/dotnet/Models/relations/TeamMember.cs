using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace dotnet.Models
{
    // TeamMember.cs
    [Table("team_members")]
    public class TeamMember
    {
        [Key, Column("team_id")]
        public int TeamId { get; set; }

        [Key, Column("email")]
        public string Email { get; set; } = string.Empty;

        // Navigation properties
        [JsonIgnore]
        public Team Team { get; set; } = null!;
        [JsonIgnore]
        public User User { get; set; } = null!;
    }
}