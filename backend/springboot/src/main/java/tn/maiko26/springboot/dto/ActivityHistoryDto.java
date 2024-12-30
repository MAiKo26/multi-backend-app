package tn.maiko26.springboot.dto;

import lombok.Data;
import tn.maiko26.springboot.model.ActivityHistory;

import java.util.Date;

@Data
public class ActivityHistoryDto {
    private String id;
    private Date doneAt;
    private UserDto user;

    public ActivityHistoryDto(ActivityHistory activity) {
        this.id = activity.getId();
        this.doneAt = activity.getDoneAt();
        this.user = new UserDto(activity.getUser());
    }
}