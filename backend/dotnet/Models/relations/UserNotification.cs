using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace dotnet.Models;

[Table("user_notifications")]
public class UserNotification
{
    [Key, Column("user_id")]
    public string UserId { get; set; } = string.Empty;

    [Key, Column("notification_id")]
    public string NotificationId { get; set; } = string.Empty;

    // Navigation properties
    [JsonIgnore]
    public User User { get; set; } = null!;
    [JsonIgnore]
    public Notification Notification { get; set; } = null!;
}

