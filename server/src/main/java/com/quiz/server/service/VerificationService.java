package com.quiz.server.service;

import com.quiz.server.helper.EmailType;
import com.quiz.server.model.entity.VerificationToken;

public interface VerificationService {

    VerificationToken generateVerificationToken(String email);

    void verifyToken(String token, EmailType emailType);

    String getEmailByToken(String token);
}
