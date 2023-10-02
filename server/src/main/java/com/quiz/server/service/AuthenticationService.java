package com.quiz.server.service;

import com.quiz.server.model.entity.User;
import jakarta.validation.constraints.NotNull;

public interface AuthenticationService {

    boolean authenticate(String username, String password);

    boolean isUserVerified(String username);

    User getUserByToken(@NotNull String token);
}
