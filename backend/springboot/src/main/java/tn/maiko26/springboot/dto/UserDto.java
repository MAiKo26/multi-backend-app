package tn.maiko26.springboot.dto;

import tn.maiko26.springboot.model.User;

import java.util.Date;

public class UserDto {


    private String email;
    private String name;
    private String avatarUrl;
    private String phoneNumber;
    private String role;
    private Date lastLogin;


    public UserDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.avatarUrl = user.getAvatarUrl();
    }

    public UserDto(String email, String name, String avatarUrl, String phoneNumber, String role, Date lastLogin) {
        this.email = email;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.lastLogin = lastLogin;
    }

    public UserDto() {
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getRole() {
        return this.role;
    }

    public Date getLastLogin() {
        return this.lastLogin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserDto)) return false;
        final UserDto other = (UserDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$avatarUrl = this.getAvatarUrl();
        final Object other$avatarUrl = other.getAvatarUrl();
        if (this$avatarUrl == null ? other$avatarUrl != null : !this$avatarUrl.equals(other$avatarUrl)) return false;
        final Object this$phoneNumber = this.getPhoneNumber();
        final Object other$phoneNumber = other.getPhoneNumber();
        if (this$phoneNumber == null ? other$phoneNumber != null : !this$phoneNumber.equals(other$phoneNumber))
            return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        final Object this$lastLogin = this.getLastLogin();
        final Object other$lastLogin = other.getLastLogin();
        if (this$lastLogin == null ? other$lastLogin != null : !this$lastLogin.equals(other$lastLogin)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $avatarUrl = this.getAvatarUrl();
        result = result * PRIME + ($avatarUrl == null ? 43 : $avatarUrl.hashCode());
        final Object $phoneNumber = this.getPhoneNumber();
        result = result * PRIME + ($phoneNumber == null ? 43 : $phoneNumber.hashCode());
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        final Object $lastLogin = this.getLastLogin();
        result = result * PRIME + ($lastLogin == null ? 43 : $lastLogin.hashCode());
        return result;
    }

    public String toString() {
        return "UserDto(email=" + this.getEmail() + ", name=" + this.getName() + ", avatarUrl=" + this.getAvatarUrl() + ", phoneNumber=" + this.getPhoneNumber() + ", role=" + this.getRole() + ", lastLogin=" + this.getLastLogin() + ")";
    }
}
