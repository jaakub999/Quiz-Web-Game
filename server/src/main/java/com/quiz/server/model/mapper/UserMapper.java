package com.quiz.server.model.mapper;

import com.quiz.server.model.dto.UserDTO;
import com.quiz.server.model.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.quiz.server.model.mapper.QuestionSetMapper.mapQuestionSetListToDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    private static UserDTO mapUserToDto(User source) {
        return UserDTO.builder()
                .username(source.getUsername())
                .password(source.getPasswordHash())
                .email(source.getEmail())
                .verified(source.getVerified())
                .questionSets(mapQuestionSetListToDto(source.getQuestionSets()))
                .build();
    }

    protected static List<UserDTO> mapUserListToDto(List<User> source) {
        return source.stream()
                .map(UserMapper::mapUserToDto)
                .collect(Collectors.toList());
    }
}
