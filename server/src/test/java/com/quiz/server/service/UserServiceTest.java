package com.quiz.server.service;

import com.quiz.server.model.entity.User;
import com.quiz.server.repository.UserRepository;
import com.quiz.server.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void createUserTest() {
        String username = "test_username";
        String password = "test_password";
        String email = "test@example.com";

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setVerified(false);

        when(passwordEncoder.encode(password)).thenReturn(password);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(username, password, email);

        verify(userRepository, times(1)).existsByUsername(username);
        verify(userRepository, times(1)).existsByEmail(email);
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(username, createdUser.getUsername());
        assertEquals(email, createdUser.getEmail());
        assertFalse(createdUser.getVerified());
    }
}
