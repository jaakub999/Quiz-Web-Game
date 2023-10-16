package com.quiz.server.service;

import com.quiz.server.model.entity.Session;
import com.quiz.server.model.entity.User;
import com.quiz.server.helper.Mode;
import com.quiz.server.repository.SessionRepository;
import com.quiz.server.repository.UserRepository;
import com.quiz.server.service.impl.SessionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {

    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void createSessionTest() {
        String token = "token";

        when(authenticationService.getUserByToken(token)).thenReturn(new User());

        String result = sessionService.createSession(token, Mode.CLASSIC);
        Optional<Session> session = sessionRepository.findByCode(result);

        verify(sessionRepository, times(1)).save(any(Session.class));
        verify(userRepository, times(1)).save(any(User.class));

        assertNotNull(session);
    }
}
