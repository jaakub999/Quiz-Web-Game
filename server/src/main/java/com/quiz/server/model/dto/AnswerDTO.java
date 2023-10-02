package com.quiz.server.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDTO {
    private String content;
    private Boolean correct;
}
