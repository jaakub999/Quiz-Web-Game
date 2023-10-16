package com.quiz.server.service;

import com.quiz.server.model.dto.PublicQuestionSetDTO;

import java.util.List;

public interface PublicQuestionSetService {

    List<PublicQuestionSetDTO> getPublicQuestionSets();
}
