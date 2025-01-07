using dotnet.Data;
using dotnet.exceptions;
using dotnet.Interfaces;
using dotnet.Models;
using Microsoft.AspNetCore.Identity;

namespace dotnet.Services;

public class ProfileService : IProfileService
{
    
     private readonly DataContext _context;
   private readonly IUserService _userService;
   private readonly IPasswordHasher<User> _passwordHasher;

   public ProfileService(DataContext context, IUserService userService, IPasswordHasher<User> passwordHasher)
   {
       _context = context;
       _userService = userService;
       _passwordHasher = passwordHasher;
        _userService = userService;

    }

    public User GetCurrentUser()
   {
       var email = _userService.GetCurrentUserEmail();
       return _context.Users.FirstOrDefault(u => u.Email == email) ?? 
              throw new CustomException("User not found", 400);
   }

   public User UpdateProfile(string name, string phoneNumber, string avatarPath)
   {
       var user = GetCurrentUser();

       if (name != null) user.Name = name;
       if (phoneNumber != null) user.PhoneNumber = phoneNumber;
       if (avatarPath != null) user.AvatarUrl = avatarPath;

       _context.Update(user);
       _context.SaveChanges();
       return user;
   }

   public void UpdatePassword(string currentPassword, string newPassword)
   {
       var user = GetCurrentUser();

       var passwordVerification = _passwordHasher.VerifyHashedPassword(user, user.Password, currentPassword);
       if (passwordVerification == PasswordVerificationResult.Failed)
       {
           throw new CustomException("Current password is incorrect", 400);
       }

       user.Password = _passwordHasher.HashPassword(user, newPassword);
       _context.Update(user);
       _context.SaveChanges();
   }

   public void UpdateNotificationSettings(UserSetting newSetting)
   {
       var user = GetCurrentUser();
       var currentSetting = _context.UserSettings.FirstOrDefault(s => s.User == user) ?? 
                           new UserSetting { User = user };

       if (newSetting.EmailDigest != null)
           currentSetting.EmailDigest = newSetting.EmailDigest;
       if (newSetting.TaskReminders != null)
           currentSetting.TaskReminders = newSetting.TaskReminders;

       _context.UserSettings.Update(currentSetting);
       _context.SaveChanges();
   }
}