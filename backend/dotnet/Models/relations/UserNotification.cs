using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dotnet.Models;

[Table("user_notifications")]
public class UserNotification
{
    [Key, Column("user_id")]
    public string UserId { get; set; } = string.Empty;

    [Key, Column("notification_id")]
    public string NotificationId { get; set; } = string.Empty;

    // Navigation properties
    public User User { get; set; } = null!;
    public Notification Notification { get; set; } = null!;
}

