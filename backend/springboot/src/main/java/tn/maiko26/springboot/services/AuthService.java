package tn.maiko26.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.models.User;
import tn.maiko26.springboot.models.Session;
import tn.maiko26.springboot.repository.SessionRepository;
import tn.maiko26.springboot.repository.UserRepository;
import tn.maiko26.springboot.utils.JwtUtil;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Invalid email"));

        if (user.getAccountLockedUntil() != null && user.getAccountLockedUntil().after(new Date())) {
            throw new Exception("Account is locked. Try again later.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= 5) {
                user.setAccountLockedUntil(new Date(System.currentTimeMillis() + 15 * 60 * 1000));
                user.setFailedLoginAttempts(0);
            }
            userRepository.save(user);
            throw new Exception("Invalid password");
        }

        if (!user.getIsVerified()) {
            throw new Exception("Email not verified.");
        }

        // Generate JWT and save session
        String token = jwtUtil.generateToken(user.getEmail());
        Session session = new Session();
        session.setSessionId(token);
        session.setEmail(user.getEmail());
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
}
