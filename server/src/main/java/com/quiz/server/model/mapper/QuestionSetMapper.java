package com.quiz.server.model.mapper;

import com.quiz.server.model.dto.QuestionSetDTO;
import com.quiz.server.model.entity.QuestionSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.quiz.server.model.mapper.QuestionMapper.mapDtoListToQuestions;
import static com.quiz.server.model.mapper.QuestionMapper.mapQuestionListToDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionSetMapper {

    public static QuestionSet mapDtoToQuestionSet(QuestionSetDTO dto) {
        return QuestionSet.builder()
                .name(dto.getName())
                .publicAccess(dto.getPublicAccess())
                .questions(mapDtoListToQuestions(dto.getQuestions()))
                .build();
    }

    public static QuestionSetDTO mapQuestionSetToDto(QuestionSet source) {
        return QuestionSetDTO.builder()
                .name(source.getName())
                .publicAccess(source.getPublicAccess())
                .keyId(source.getKeyId())
                .questions(mapQuestionListToDto(source.getQuestions()))
                .build();
    }

    public static List<QuestionSetDTO> mapQuestionSetListToDto(List<QuestionSet> source) {
        return source.stream()
                .map(QuestionSetMapper::mapQuestionSetToDto)
                .collect(Collectors.toList());
    }
}
