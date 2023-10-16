package com.quiz.server.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicQuestionSetDTO {
    private String questionSetKeyId;
    private String questionSetName;
    private String userUsername;
}
