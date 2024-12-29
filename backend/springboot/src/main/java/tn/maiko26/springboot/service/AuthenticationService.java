package tn.maiko26.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.CustomException;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.Session;
import tn.maiko26.springboot.repository.SessionRepository;
import tn.maiko26.springboot.repository.UserRepository;
import tn.maiko26.springboot.util.JwtUtil;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final SecureRandom random = new SecureRandom();
    @Autowired
    private EmailSenderService emailSenderService;

    public String login(String email, String password)  {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Invalid email",401));


        if (user.getAccountLockedUntil() != null && user.getAccountLockedUntil().after(new Date())) {
            throw new CustomException("Account is locked. Try again later.",401);
        }


        if (!passwordEncoder.matches(password, user.getPassword())) {

            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= 5) {
                user.setAccountLockedUntil(new Date(System.currentTimeMillis() + 15 * 60 * 1000));
                user.setFailedLoginAttempts(0);
            }
            userRepository.save(user);
            throw new CustomException("Invalid password",401);
        }

        if (!user.getIsVerified()) {
            throw new CustomException("Email not verified.",401);
        }

        // Generate JWT and save session
        String token = jwtUtil.generateToken(user.getEmail());
        Session session = new Session();
        session.setSessionId(token);
        session.setUser(user);
        session.setCreatedAt(new Date());
        session.setExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000));
        sessionRepository.save(session);

        user.setFailedLoginAttempts(0);
        user.setLastLogin(new Date());
        userRepository.save(user);

        return token;
    }

    public void logout(String token) {
        if (jwtUtil.validateToken(token)) {
            sessionRepository.deleteBySessionId(token);
        }
    }

    public void register(String email, String password,String name) {
        if (userRepository.findByEmail(email).isPresent()) throw new IllegalArgumentException("Email already exists");


        String hashedPassword = passwordEncoder.encode(password);

        User user = new User(email, hashedPassword, name);


        byte[] verificationTokenBytes = new byte[32];
        random.nextBytes(verificationTokenBytes);
        String verificationToken = Base64.getEncoder().encodeToString(verificationTokenBytes);

        user.setVerificationToken(verificationToken);
        user.setIsVerified(false);
        user.setCreatedAt(new Date());
        userRepository.save(user);

        emailSenderService.sendVerificationEmail(email, verificationToken);

    }

    public void registerVerification(String verificationToken) {

        log.error(verificationToken);
        Optional<User> existingUser = userRepository.findByVerificationToken(verificationToken);

        log.error(String.valueOf(existingUser));

        if (existingUser.isEmpty()) throw new IllegalArgumentException("Invalid verification token");

        User user = existingUser.get();

        user.setIsVerified(true);
        userRepository.save(user);


    }

    public void passwordReset(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) throw new IllegalArgumentException("Email not found");

        User user = existingUser.get();

        byte[] resetPasswordTokenBytes = new byte[32];
        random.nextBytes(resetPasswordTokenBytes);
        String resetPasswordToken = Base64.getEncoder().encodeToString(resetPasswordTokenBytes);

        user.setResetPasswordToken(resetPasswordToken);
        user.setResetPasswordExpiry(new Date(System.currentTimeMillis() + 3600 * 1000));

        userRepository.save(user);

        emailSenderService.sendResetEmail(email, resetPasswordToken);

    }

    public void passwordResetVerification(String resetPasswordToken) {

        Date now = new Date();

        List<User> users = userRepository.findAllByResetPasswordTokenAndResetPasswordExpiryGreaterThan(resetPasswordToken, now);

        if (users.isEmpty()) throw new IllegalArgumentException("Invalid or expired reset token");

        for (User user : users) {
            user.setResetPasswordToken(null);
            user.setResetPasswordExpiry(null);
            userRepository.save(user);
        }


    }

    public void passwordResetConfirmation(String resetPasswordToken, String newPassword) {

        String hashedPassword = passwordEncoder.encode(newPassword);

        User user = userRepository.findByResetPasswordToken(resetPasswordToken);

        user.setResetPasswordExpiry(null);
        user.setResetPasswordToken(null);

        userRepository.save(user);



    }


}
