package com.quiz.server.model.mapper;

import com.quiz.server.model.dto.AnswerDTO;
import com.quiz.server.model.entity.Answer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerMapper {

    private static Answer mapDtoToAnswer(AnswerDTO dto) {
        return Answer.builder()
                .content(dto.getContent())
                .correct(dto.getCorrect())
                .build();
    }

    private static AnswerDTO mapAnswerToDto(Answer source) {
        return AnswerDTO.builder()
                .content(source.getContent())
                .correct(source.getCorrect())
                .build();
    }

    protected static List<Answer> mapDtoListToAnswers(List<AnswerDTO> dtos) {
        return dtos.stream()
                .map(AnswerMapper::mapDtoToAnswer)
                .collect(Collectors.toList());
    }

    protected static List<AnswerDTO> mapAnswerListToDto(List<Answer> source) {
        return source.stream()
                .map(AnswerMapper::mapAnswerToDto)
                .collect(Collectors.toList());
    }
}
