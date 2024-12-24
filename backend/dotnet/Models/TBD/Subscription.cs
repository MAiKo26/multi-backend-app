using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("subscriptions")]
    public class Subscription
    {
        [Key]
        [Required]
        [Column("subscription_id")]
        public string Id { get; set; } = string.Empty;

        [ForeignKey("User")]
        [Column("user_id")]
        public string UserId { get; set; } = string.Empty;
        public User User { get; set; }

        [Required]
        [Column("plan")]
        public string Plan { get; set; } = string.Empty;

        [Required]
        [Column("status")]
        public string Status { get; set; } = string.Empty;

        [Required]
        [Column("start_date")]
        public DateTime StartDate { get; set; }

        [Column("end_date")]
        public DateTime? EndDate { get; set; }

        [Required]
        [Column("price", TypeName = "decimal(18,2)")]
        public decimal Price { get; set; }

        [Required]
        [Column("billing_cycle")]
        public string BillingCycle { get; set; } = string.Empty;
    }
}
