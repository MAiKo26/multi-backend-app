package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.Notification;
import tn.maiko26.springboot.model.Session;

public interface NotificationRepository extends JpaRepository<Notification, String> {
}
