package com.quiz.server.service.impl;

import com.quiz.server.model.dto.QuestionSetDTO;
import com.quiz.server.model.entity.QuestionSet;
import com.quiz.server.model.entity.User;
import com.quiz.server.repository.QuestionSetRepository;
import com.quiz.server.repository.UserRepository;
import com.quiz.server.service.AuthenticationService;
import com.quiz.server.service.QuestionSetService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.quiz.server.model.mapper.QuestionSetMapper.mapDtoToQuestionSet;
import static com.quiz.server.model.mapper.QuestionSetMapper.mapQuestionSetListToDto;

@Service
@AllArgsConstructor
public class QuestionSetServiceImpl implements QuestionSetService {

    private final UserRepository userRepository;
    private final QuestionSetRepository questionSetRepository;
    private final AuthenticationService authenticationService;

    @Override
    @Transactional
    public void createQuestionSet(String token, QuestionSetDTO dto) {
        QuestionSet questionSet = mapDtoToQuestionSet(dto);
        User user = authenticationService.getUserByToken(token);

        questionSet.setUser(user);
        user.getQuestionSets().add(questionSet);

        userRepository.save(user);
    }

    @Override
    public List<QuestionSetDTO> getUserQuestionSets(String token) {
        User user = authenticationService.getUserByToken(token);
        List<QuestionSet> questionSets = questionSetRepository.getQuestionSetsByUser(user);

        return mapQuestionSetListToDto(questionSets);
    }
}
