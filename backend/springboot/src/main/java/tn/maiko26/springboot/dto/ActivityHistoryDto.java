package tn.maiko26.springboot.dto;

import tn.maiko26.springboot.model.ActivityHistory;

import java.util.Date;

public class ActivityHistoryDto {
    private String id;
    private Date doneAt;
    private UserDto user;

    public ActivityHistoryDto(ActivityHistory activity) {
        this.id = activity.getId();
        this.doneAt = activity.getDoneAt();
        this.user = new UserDto(activity.getUser());
    }

    public String getId() {
        return this.id;
    }

    public Date getDoneAt() {
        return this.doneAt;
    }

    public UserDto getUser() {
        return this.user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDoneAt(Date doneAt) {
        this.doneAt = doneAt;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ActivityHistoryDto)) return false;
        final ActivityHistoryDto other = (ActivityHistoryDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$doneAt = this.getDoneAt();
        final Object other$doneAt = other.getDoneAt();
        if (this$doneAt == null ? other$doneAt != null : !this$doneAt.equals(other$doneAt)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ActivityHistoryDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $doneAt = this.getDoneAt();
        result = result * PRIME + ($doneAt == null ? 43 : $doneAt.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        return result;
    }

    public String toString() {
        return "ActivityHistoryDto(id=" + this.getId() + ", doneAt=" + this.getDoneAt() + ", user=" + this.getUser() + ")";
    }
}