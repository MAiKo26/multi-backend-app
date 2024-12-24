using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("team_members")]
    public class TeamMember
    {
        [Key]
        [Required]
        [Column("team_member_id")]
        public string Id { get; set; } = string.Empty;

        [Required]
        [ForeignKey("Team")]
        [Column("team_id")]
        public string TeamId { get; set; } = string.Empty;
        public Team Team { get; set; }

        [Required]
        [ForeignKey("User")]
        [Column("email")]
        public string Email { get; set; } = string.Empty;
        public User User { get; set; }

        [Column("role")]
        public string Role { get; set; } = "user";
    }
}