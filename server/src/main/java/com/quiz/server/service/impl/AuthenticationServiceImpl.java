package com.quiz.server.service.impl;

import com.quiz.server.exception.QRuntimeException;
import com.quiz.server.model.entity.User;
import com.quiz.server.security.JwtTokenProvider;
import com.quiz.server.service.AuthenticationService;
import com.quiz.server.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.quiz.server.exception.ExceptionCode.E008;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtTokenProvider provider;

    @Override
    public boolean authenticate(String username, String password) {
        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String passwordHash = user.getPasswordHash();

            return BCrypt.checkpw(password, passwordHash);
        } else return false;
    }

    @Override
    public boolean isUserVerified(String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(User::getVerified).orElse(false);
    }

    @Override
    public User getUserByToken(String token) {
        Claims claims = provider.parseToken(token);
        Long id = Long.parseLong(claims.getSubject());
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent())
            return user.get();

        else throw new QRuntimeException(E008);
    }
}
