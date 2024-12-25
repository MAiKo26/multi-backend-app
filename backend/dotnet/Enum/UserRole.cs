using System.ComponentModel;

namespace dotnet.Models
{
    public enum UserRole
    {
        Admin,
        User
    }

    public static class UserRoleExtensions
    {
        public static string ToStringValue(this UserRole role)
        {
            return role.ToString().ToLower();
        }
    }
}