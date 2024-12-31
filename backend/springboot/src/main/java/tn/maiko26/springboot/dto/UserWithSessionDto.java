package tn.maiko26.springboot.dto;

public class UserWithSessionDto {
    private String sessionId;
    private String email;
    private String name;
    private String avatar;
    private String role;
    private String phoneNumber;
    private String lastLogin;


    public UserWithSessionDto(String sessionId, String email, String name, String avatar, String role, String phoneNumber, String lastLogin) {
        this.sessionId = sessionId;
        this.email = email;
        this.name = name;
        this.avatar = avatar;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.lastLogin = lastLogin;
    }

    public UserWithSessionDto() {
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getRole() {
        return this.role;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getLastLogin() {
        return this.lastLogin;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserWithSessionDto)) return false;
        final UserWithSessionDto other = (UserWithSessionDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$sessionId = this.getSessionId();
        final Object other$sessionId = other.getSessionId();
        if (this$sessionId == null ? other$sessionId != null : !this$sessionId.equals(other$sessionId)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$avatar = this.getAvatar();
        final Object other$avatar = other.getAvatar();
        if (this$avatar == null ? other$avatar != null : !this$avatar.equals(other$avatar)) return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        final Object this$phoneNumber = this.getPhoneNumber();
        final Object other$phoneNumber = other.getPhoneNumber();
        if (this$phoneNumber == null ? other$phoneNumber != null : !this$phoneNumber.equals(other$phoneNumber))
            return false;
        final Object this$lastLogin = this.getLastLogin();
        final Object other$lastLogin = other.getLastLogin();
        if (this$lastLogin == null ? other$lastLogin != null : !this$lastLogin.equals(other$lastLogin)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserWithSessionDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $sessionId = this.getSessionId();
        result = result * PRIME + ($sessionId == null ? 43 : $sessionId.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $avatar = this.getAvatar();
        result = result * PRIME + ($avatar == null ? 43 : $avatar.hashCode());
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        final Object $phoneNumber = this.getPhoneNumber();
        result = result * PRIME + ($phoneNumber == null ? 43 : $phoneNumber.hashCode());
        final Object $lastLogin = this.getLastLogin();
        result = result * PRIME + ($lastLogin == null ? 43 : $lastLogin.hashCode());
        return result;
    }

    public String toString() {
        return "UserWithSessionDto(sessionId=" + this.getSessionId() + ", email=" + this.getEmail() + ", name=" + this.getName() + ", avatar=" + this.getAvatar() + ", role=" + this.getRole() + ", phoneNumber=" + this.getPhoneNumber() + ", lastLogin=" + this.getLastLogin() + ")";
    }
}
