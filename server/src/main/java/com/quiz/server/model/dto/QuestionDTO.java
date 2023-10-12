package com.quiz.server.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionDTO {
    private String content;
    private String category;
    private Integer points;
    private String image;
    private String imageName;
    private List<AnswerDTO> answers;
}
