package com.quiz.server.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private boolean keepLogged;
}
