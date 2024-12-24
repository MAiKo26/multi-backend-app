using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models
{
    [Table("chat_messages")]
    public class ChatMessage
    {
        [Key]
        [Required]
        [Column("message_id")]
        public string Id { get; set; } = string.Empty;

        [Required]
        [ForeignKey("ChatRoom")]
        [Column("room_id")]
        public string RoomId { get; set; } = string.Empty;
        public ChatRoom ChatRoom { get; set; }

        [Required]
        [ForeignKey("User")]
        [Column("sender_id")]
        public string SenderId { get; set; } = string.Empty;
        public User Sender { get; set; }

        [Required]
        [Column("content")]
        public string Content { get; set; } = string.Empty;

        [Column("created_at")]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        [Column("read_by")]
        public List<string> ReadBy { get; set; } = new List<string>();
    }
}