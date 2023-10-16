package com.quiz.server.model.mapper;

import com.quiz.server.model.dto.PublicQuestionSetDTO;
import com.quiz.server.model.view.PublicQuestionSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicQuestionSetMapper {

    private static PublicQuestionSetDTO mapPublicQuestionSetToDto(PublicQuestionSet source) {
        return PublicQuestionSetDTO.builder()
                .questionSetKeyId(source.getQuestionSetKeyId())
                .questionSetName(source.getQuestionSetName())
                .userUsername(source.getUserUsername())
                .build();
    }

    public static List<PublicQuestionSetDTO> mapPublicQuestionSetListToDto(List<PublicQuestionSet> source) {
        return source.stream()
                .map(PublicQuestionSetMapper::mapPublicQuestionSetToDto)
                .collect(Collectors.toList());
    }
}
