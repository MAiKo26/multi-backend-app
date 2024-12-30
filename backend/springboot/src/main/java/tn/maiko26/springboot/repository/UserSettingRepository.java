package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.Session;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.UserSetting;

import java.util.Optional;

public interface UserSettingRepository extends JpaRepository<UserSetting, String> {
    Optional<UserSetting> findByUser(User user);
}
