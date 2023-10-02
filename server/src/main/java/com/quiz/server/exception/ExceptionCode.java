package com.quiz.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionCode {

    E001("User not found"),
    E002("User with that username or email already exists"),
    E003("Invalid email address"),
    E004("Token not found"),
    E005("Token has expired"),
    E006("User is already verified"),
    E007("JWT token date expired"),
    E008("JWT token is invalid"),
    E009("Passwords do not match");

    private final String message;
}
