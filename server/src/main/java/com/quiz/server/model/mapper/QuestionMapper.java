package com.quiz.server.model.mapper;

import com.quiz.server.model.dto.QuestionDTO;
import com.quiz.server.model.entity.Question;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.quiz.server.model.mapper.AnswerMapper.mapAnswerListToDto;
import static com.quiz.server.model.mapper.AnswerMapper.mapDtoListToAnswers;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionMapper {

    public static Question mapDtoToQuestion(QuestionDTO dto) {
        return Question.builder()
                .content(dto.getContent())
                .category(dto.getCategory())
                .points(dto.getPoints())
                .image(dto.getImage())
                .imageName(dto.getImageName())
                .answers(mapDtoListToAnswers(dto.getAnswers()))
                .build();
    }

    public static QuestionDTO mapQuestionToDto(Question source) {
        return QuestionDTO.builder()
                .content(source.getContent())
                .category(source.getCategory())
                .points(source.getPoints())
                .image(source.getImage())
                .imageName(source.getImageName())
                .answers(mapAnswerListToDto(source.getAnswers()))
                .build();
    }

    public static List<Question> mapDtoListToQuestions(List<QuestionDTO> dtos) {
        return dtos.stream()
                .map(QuestionMapper::mapDtoToQuestion)
                .collect(Collectors.toList());
    }

    public static List<QuestionDTO> mapQuestionListToDto(List<Question> source) {
        return source.stream()
                .map(QuestionMapper::mapQuestionToDto)
                .collect(Collectors.toList());
    }
}
