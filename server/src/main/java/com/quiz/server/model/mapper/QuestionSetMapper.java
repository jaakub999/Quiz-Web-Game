package com.quiz.server.model.mapper;

import com.quiz.server.model.dto.QuestionSetDTO;
import com.quiz.server.model.entity.QuestionSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.quiz.server.model.mapper.QuestionMapper.mapDtoListToQuestions;
import static com.quiz.server.model.mapper.QuestionMapper.mapQuestionListToDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionSetMapper {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Integer LENGTH = 30;

    public static QuestionSet mapDtoToQuestionSet(QuestionSetDTO dto) {
        return QuestionSet.builder()
                .name(dto.getName())
                .publicAccess(dto.getPublicAccess())
                .questions(mapDtoListToQuestions(dto.getQuestions()))
                .keyId(dto.getKeyId() == null ? generateKeyId(dto.getName()) : dto.getKeyId())
                .build();
    }

    public static QuestionSetDTO mapQuestionSetToDto(QuestionSet source) {
        return QuestionSetDTO.builder()
                .name(source.getName())
                .publicAccess(source.getPublicAccess())
                .questions(mapQuestionListToDto(source.getQuestions()))
                .keyId(source.getKeyId())
                .build();
    }

    public static List<QuestionSetDTO> mapQuestionSetListToDto(List<QuestionSet> source) {
        return source.stream()
                .map(QuestionSetMapper::mapQuestionSetToDto)
                .collect(Collectors.toList());
    }

    private static String generateKeyId(String setName) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);

        IntStream.range(0, LENGTH).map(i -> random.nextInt(CHARACTERS.length()))
                .forEach(randomIndex -> {
            sb.append(CHARACTERS.charAt(randomIndex));
        });

        return "key_" + setName + "_" + sb;
    }
}
