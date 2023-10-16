package com.quiz.server.service.impl;

import com.quiz.server.helper.EmailType;
import com.quiz.server.model.entity.User;
import com.quiz.server.model.entity.VerificationToken;
import com.quiz.server.properties.AppProperties;
import com.quiz.server.service.EmailService;
import com.quiz.server.service.VerificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;
    private final VerificationService verificationService;
    private final AppProperties appProperties;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,
                            VerificationService verificationService,
                            AppProperties appProperties) {

        this.javaMailSender = javaMailSender;
        this.verificationService = verificationService;
        this.appProperties = appProperties;
    }

    @Override
    public void sendEmail(User user, EmailType type) {
        new Thread(() -> {
            String email = user.getEmail();
            VerificationToken verificationToken = verificationService.generateVerificationToken(email);
            String htmlBody = createBody(user.getUsername(), verificationToken.getToken(), type);

            try {
                MimeMessage message = generateMessage(email, type.getSubject(), htmlBody);
                javaMailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String createBody(String username, String token, EmailType type) {
        String verificationUrl;

        if (type == EmailType.REGISTER) {
            verificationUrl = appProperties.getVerification().getUrl();

            return "<html><body><h2>Hello " + username + ",</h2>" +
                    "<p>Please click on the link below to verify your email address:</p>" +
                    "<a href='" + verificationUrl + "?token=" + token + "'>Verify your email address</a>" +
                    "<p>If you did not request this verification, please ignore this email.</p>";
        }

        else if (type == EmailType.PASSWORD) {
            verificationUrl = appProperties.getUserPassword().getUrl();

            return "<html><body><h2>Hello " + username + ",</h2>" +
                    "<p>Please click on the link below to reset your password:</p>" +
                    "<a href='" + verificationUrl + "?token=" + token + "'>Reset Password</a>" +
                    "<p>If you did not request changing your password, please ignore this email.</p>";
        }

        return null;
    }

    private MimeMessage generateMessage(String email, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        return message;
    }
}
