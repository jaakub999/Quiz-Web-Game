package com.quiz.server.service.impl;

import com.quiz.server.exception.QRuntimeException;
import com.quiz.server.model.dto.AnswerDTO;
import com.quiz.server.model.dto.QuestionDTO;
import com.quiz.server.model.dto.QuestionSetDTO;
import com.quiz.server.model.entity.Answer;
import com.quiz.server.model.entity.Question;
import com.quiz.server.model.entity.QuestionSet;
import com.quiz.server.model.entity.User;
import com.quiz.server.model.mapper.QuestionSetMapper;
import com.quiz.server.repository.QuestionSetRepository;
import com.quiz.server.repository.UserRepository;
import com.quiz.server.service.AuthenticationService;
import com.quiz.server.service.QuestionSetService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.quiz.server.exception.ExceptionCode.E010;
import static com.quiz.server.model.mapper.QuestionSetMapper.mapDtoToQuestionSet;
import static com.quiz.server.model.mapper.QuestionSetMapper.mapQuestionSetListToDto;

@Service
@AllArgsConstructor
public class QuestionSetServiceImpl implements QuestionSetService {

    private static final Integer ANSWERS_LENGTH = 4;

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

        for (Question question : questionSet.getQuestions()) {
            question.setQuestionSet(questionSet);
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
            }
        }

        userRepository.save(user);
    }

    @Override
    public List<QuestionSetDTO> getUserQuestionSets(String token) {
        User user = authenticationService.getUserByToken(token);
        List<QuestionSet> questionSets = questionSetRepository.findByUser(user);

        return mapQuestionSetListToDto(questionSets);
    }

    @Override
    public Optional<QuestionSetDTO> getQuestionSet(String keyId) {
        Optional<QuestionSet> questionSet = Optional.ofNullable(questionSetRepository.findByKeyId(keyId)
                .orElseThrow(() -> new QRuntimeException(E010)));

        return questionSet.map(QuestionSetMapper::mapQuestionSetToDto);
    }

    @Override
    @Transactional
    public void deleteQuestionSet(String keyId) {
        questionSetRepository.deleteByKeyId(keyId);
    }

    @Override
    @Transactional
    public void editQuestionSet(QuestionSetDTO dto) {
        Optional<QuestionSet> optionalQuestionSet = questionSetRepository.findByKeyId(dto.getKeyId());

        if (optionalQuestionSet.isEmpty()) {
            throw new QRuntimeException(E010);
        }

        QuestionSet questionSet = optionalQuestionSet.get();
        questionSet.setName(dto.getName());
        questionSet.setPublicAccess(dto.getPublicAccess());

        List<Question> existingQuestions = questionSet.getQuestions();
        List<QuestionDTO> newQuestions = dto.getQuestions();

        while (existingQuestions.size() > newQuestions.size()) {
            existingQuestions.remove(existingQuestions.size() - 1);
        }

        for (var i = 0; i < newQuestions.size(); i++) {
            boolean isNewQuestion;
            Question question;

            if (i < existingQuestions.size()) {
                question = existingQuestions.get(i);
                isNewQuestion = false;
            } else {
                question = new Question();
                question.setQuestionSet(questionSet);
                existingQuestions.add(question);
                isNewQuestion = true;
            }

            QuestionDTO questionDTO = dto.getQuestions().get(i);
            updateQuestion(question, questionDTO);

            for (var j = 0; j < ANSWERS_LENGTH; j++) {
                Answer answer;

                if (!isNewQuestion) {
                    answer = question.getAnswers().get(j);
                } else {
                    answer = new Answer();
                    answer.setQuestion(question);
                    question.getAnswers().add(answer);
                }

                AnswerDTO answerDTO = questionDTO.getAnswers().get(j);
                updateAnswer(answer, answerDTO);
            }
        }

        questionSetRepository.save(questionSet);
    }

    private void updateQuestion(Question question, QuestionDTO dto) {
        question.setContent(dto.getContent());
        question.setCategory(dto.getCategory());
        question.setPoints(dto.getPoints());
        question.setImage(dto.getImage());
        question.setImageName(dto.getImageName());
    }

    private void updateAnswer(Answer answer, AnswerDTO dto) {
        answer.setContent(dto.getContent());
        answer.setCorrect(dto.getCorrect());
    }
}
