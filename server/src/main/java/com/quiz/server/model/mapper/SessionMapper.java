package com.quiz.server.model.mapper;

import com.quiz.server.model.dto.SessionDTO;
import com.quiz.server.model.entity.Session;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.quiz.server.model.mapper.UserMapper.mapUserListToDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionMapper {

    public static SessionDTO mapSessionToDto(Session source) {
        return SessionDTO.builder()
                .host(source.getHost())
                .code(source.getCode())
                .active(source.getActive())
                .mode(source.getMode())
                .players(mapUserListToDto(source.getPlayers()))
                .build();
    }
}
