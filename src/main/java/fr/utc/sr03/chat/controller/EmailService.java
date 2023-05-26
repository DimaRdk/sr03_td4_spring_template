package fr.utc.sr03.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;


    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPasswordToUser(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("moins.termost@gmail.com");
        message.setTo(to);
        message.setSubject("Your password");
        message.setText("Hello, here is your password: " + password);
        javaMailSender.send(message);
    }
}
