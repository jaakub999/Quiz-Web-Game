package com.quiz.server.response;

import com.quiz.server.model.dto.SessionDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionResponse {

    @NotNull
    private SessionDTO session;

    @NotNull
    private String username;
}
