package com.quiz.server.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Boolean verified;
    private List<QuestionSetDTO> questionSets;
}
