package com.quiz.server.model.dto;

import com.quiz.server.helper.Mode;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SessionDTO {
    private String host;
    private String code;
    private Boolean active;
    private Mode mode;
    private List<UserDTO> players;
}