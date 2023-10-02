package com.quiz.server.service;

import com.quiz.server.model.dto.QuestionSetDTO;
import com.quiz.server.model.entity.User;
import com.quiz.server.repository.UserRepository;
import com.quiz.server.service.impl.QuestionSetServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class QuestionSetServiceTest {

    @InjectMocks
    private QuestionSetServiceImpl questionSetService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    public void createQuestionSetTest() {
        String token = "token";
        QuestionSetDTO dto = mock(QuestionSetDTO.class);
        User user = new User();
        user.setId(1L);

        when(authenticationService.getUserByToken(token)).thenReturn(user);

        questionSetService.createQuestionSet(token, dto);

        verify(authenticationService, times(1)).getUserByToken(token);
        verify(userRepository, times(1)).save(user);

        assertNotNull(user.getQuestionSets());
    }
}
