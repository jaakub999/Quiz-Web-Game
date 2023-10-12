package com.quiz.server.service;

import com.quiz.server.model.dto.QuestionSetDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface QuestionSetService {

    void createQuestionSet(@NotNull String token, QuestionSetDTO dto);

    List<QuestionSetDTO> getUserQuestionSets(@NotNull String token);

    Optional<QuestionSetDTO> getQuestionSet(String keyId);

    void deleteQuestionSet(String keyId);

    void editQuestionSet(QuestionSetDTO dto);
}
