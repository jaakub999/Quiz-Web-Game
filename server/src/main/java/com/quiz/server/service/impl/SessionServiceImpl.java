package com.quiz.server.service.impl;

import com.quiz.server.exception.QRuntimeException;
import com.quiz.server.helper.Mode;
import com.quiz.server.model.dto.SessionDTO;
import com.quiz.server.model.entity.Session;
import com.quiz.server.model.entity.User;
import com.quiz.server.repository.SessionRepository;
import com.quiz.server.repository.UserRepository;
import com.quiz.server.service.AuthenticationService;
import com.quiz.server.service.SessionService;
import com.quiz.server.service.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static com.quiz.server.exception.ExceptionCode.E011;
import static com.quiz.server.model.mapper.SessionMapper.mapSessionToDto;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Integer CODE_LENGTH = 6;

    private final WebSocketService webSocketService;
    private final AuthenticationService authenticationService;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public String createSession(String token, Mode mode) {
        String code;

        do {
            code = generateCode();
        } while (sessionRepository.existsByCode(code));

        User host = authenticationService.getUserByToken(token);
        Session session = Session.builder()
                .host(host.getUsername())
                .code(code)
                .active(true)
                .mode(mode)
                .players(new ArrayList<>())
                .build();

        session.getPlayers().add(host);
        host.setSession(session);
        userRepository.save(host);
        sessionRepository.save(session);

        return code;
    }

    @Override
    public SessionDTO getSession(String code) {
        return mapSessionToDto(findSession(code));
    }

    @Override
    @Transactional
    public void joinSession(String token, String code) {
        User user = authenticationService.getUserByToken(token);
        Session session = findSession(code);
        session.getPlayers().add(user);
        user.setSession(session);
        userRepository.save(user);
        String topic = "session/join/" + code;
        notifyFrontend(topic);
    }

    private Session findSession(String code) {
        return sessionRepository.findByCode(code)
                .orElseThrow(() -> new QRuntimeException(E011));
    }

    private String generateCode() {
        SecureRandom random = new SecureRandom();
        return IntStream.range(0, CODE_LENGTH)
                .mapToObj(c -> CHARACTERS.charAt(random.nextInt(CHARACTERS.length())))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private void notifyFrontend(String topic) {
        if (topic == null) {
            return;
        }

        webSocketService.sendMessage(topic);
    }
}
