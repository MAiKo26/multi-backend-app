package tn.maiko26.springboot.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.ResourceNotImplementedException;
import tn.maiko26.springboot.model.ActivityHistory;

import java.util.List;

@Service
public class ActivityHistoryService {

    public List<ActivityHistory> getAllActivities() {
        throw new ResourceNotImplementedException();
    }

    public List<ActivityHistory> getAllUserActivities(String email) {
        throw new ResourceNotImplementedException();
    }

}
