package com.quiz.server.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionSetResponse {

    @NotNull
    private String name;

    @NotNull
    private String keyId;
}
