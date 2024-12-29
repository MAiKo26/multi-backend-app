package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationToken(String verificationToken);
    List<User>  findAllByResetPasswordTokenAndResetPasswordExpiryGreaterThan(String resetPasswordToken, Date resetPasswordExpiry);
    User findByResetPasswordToken(String resetPasswordToken);
}
