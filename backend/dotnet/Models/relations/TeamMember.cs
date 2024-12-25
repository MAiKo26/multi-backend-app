using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

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
        public Team Team { get; set; } = null!;
        public User User { get; set; } = null!;
    }
}