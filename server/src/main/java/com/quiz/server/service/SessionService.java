package com.quiz.server.service;

import com.quiz.server.helper.Mode;
import com.quiz.server.model.dto.SessionDTO;
import jakarta.validation.constraints.NotNull;

public interface SessionService {

    String createSession(@NotNull String token, Mode mode);

    SessionDTO getSession(String code);

    void joinSession(@NotNull String token, String code);
}
