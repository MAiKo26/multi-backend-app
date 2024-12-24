using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("starred_items")]
    public class StarredTask
    {
        [Key]
        [Required]
        [Column("star_id")]
        public string Id { get; set; } = string.Empty;

        [ForeignKey("User")]
        [Column("user_id")]
        public string UserId { get; set; } = string.Empty;
        public User User { get; set; }

        [Required]
        [Column("item_type")]
        public string ItemType { get; set; } = string.Empty;

        [Required]
        [Column("item_id")]
        public string ItemId { get; set; } = string.Empty;

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    }
}
