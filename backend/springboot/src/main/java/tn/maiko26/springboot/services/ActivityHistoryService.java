package tn.maiko26.springboot.services;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.models.ActivityHistory;

import java.util.List;

@Service
public class ActivityHistoryService {

    public List<ActivityHistory> getAllActivities() {
        throw new NotImplementedException();
    }

    public List<ActivityHistory> getAllUserActivities(String email) {
        throw new NotImplementedException();
    }

}
