package com.quiz.server.service;

import com.quiz.server.model.dto.QuestionSetDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface QuestionSetService {

    void createQuestionSet(@NotNull String token, QuestionSetDTO dto);

    List<QuestionSetDTO> getUserQuestionSets(@NotNull String token);
}
