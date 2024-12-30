package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.Session;
import tn.maiko26.springboot.model.relations.UserNotification;

public interface UserNotificationRepository extends JpaRepository<UserNotification, String> {
}
