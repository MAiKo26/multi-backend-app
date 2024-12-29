package tn.maiko26.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;



    public void sendVerificationEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jewel.green12@ethereal.email");
        message.setTo(email);
        message.setSubject("Verify your email");
        message.setText("Click here to verify: http://localhost:5173/auth/email-confirmation/" + token);
        javaMailSender.send(message);
    }

    public void sendResetEmail (String email, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jewel.green12@ethereal.email");
        message.setTo(email);
        message.setSubject("Password Reset Code");
        message.setText("Your password reset code is:: http://localhost:5173/auth/password-reset/" + token);
        javaMailSender.send(message);

    }
}
