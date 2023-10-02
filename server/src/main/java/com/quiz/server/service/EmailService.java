package com.quiz.server.service;

import com.quiz.server.helper.EmailType;
import com.quiz.server.model.entity.User;

public interface EmailService {

    void sendEmail(User user, EmailType type);
}
