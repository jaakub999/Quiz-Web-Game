package com.quiz.server.service.impl;

import com.quiz.server.model.dto.PublicQuestionSetDTO;
import com.quiz.server.model.view.PublicQuestionSet;
import com.quiz.server.repository.PublicQuestionSetRepository;
import com.quiz.server.service.PublicQuestionSetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.quiz.server.model.mapper.PublicQuestionSetMapper.mapPublicQuestionSetListToDto;

@Service
@AllArgsConstructor
public class PublicQuestionSetServiceImpl implements PublicQuestionSetService {

    private final PublicQuestionSetRepository publicQuestionSetRepository;

    @Override
    public List<PublicQuestionSetDTO> getPublicQuestionSets() {
        List<PublicQuestionSet> publicQuestionSets = publicQuestionSetRepository.findAll();
        return mapPublicQuestionSetListToDto(publicQuestionSets);
    }
}
