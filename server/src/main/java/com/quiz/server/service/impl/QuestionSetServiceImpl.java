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
import com.quiz.server.response.QuestionSetResponse;
import com.quiz.server.service.AuthenticationService;
import com.quiz.server.service.QuestionSetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.IntStream;

import static com.quiz.server.exception.ExceptionCode.E010;
import static com.quiz.server.model.mapper.QuestionSetMapper.mapDtoToQuestionSet;
import static com.quiz.server.model.mapper.QuestionSetMapper.mapQuestionSetListToDto;

@Service
@AllArgsConstructor
public class QuestionSetServiceImpl implements QuestionSetService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Integer KEY_LENGTH = 35;
    private static final Integer ANSWERS_LENGTH = 4;

    private final UserRepository userRepository;
    private final QuestionSetRepository questionSetRepository;
    private final AuthenticationService authenticationService;

    @Override
    @Transactional
    public void createQuestionSet(String token, QuestionSetDTO dto) {
        QuestionSet questionSet = mapDtoToQuestionSet(dto);
        User user = authenticationService.getUserByToken(token);
        String questionSetName = questionSet.getName();
        String username = user.getUsername();
        String keyId;

        do {
            keyId = generateKeyId(questionSetName, username);
        } while (questionSetRepository.existsByKeyId(keyId));

        questionSet.setKeyId(keyId);
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
    public List<QuestionSetResponse> getUserQuestionSets(String token) {
        User user = authenticationService.getUserByToken(token);
        List<QuestionSet> questionSets = questionSetRepository.findByUser(user);
        List<QuestionSetResponse> userSets = new ArrayList<>();

        for (QuestionSet questionSet : questionSets)
            userSets.add(new QuestionSetResponse(questionSet.getName(), questionSet.getKeyId()));

        return userSets;
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

    private String generateKeyId(String setName, String username) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(KEY_LENGTH);
        IntStream.range(0, KEY_LENGTH).map(i -> random.nextInt(CHARACTERS.length()))
                .forEach(randomIndex -> {
                    sb.append(CHARACTERS.charAt(randomIndex));
                });

        return "key_" + username + "_" + setName + "_" + sb;
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
