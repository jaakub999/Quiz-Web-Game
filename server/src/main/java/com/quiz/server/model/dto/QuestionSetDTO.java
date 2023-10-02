package com.quiz.server.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionSetDTO {
    private String name;
    private Boolean publicAccess;
    private String keyId;
    private List<QuestionDTO> questions;
}
