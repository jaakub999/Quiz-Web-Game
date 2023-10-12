package com.quiz.server.service.impl;

import com.quiz.server.exception.QRuntimeException;
import com.quiz.server.model.entity.User;
import com.quiz.server.repository.UserRepository;
import com.quiz.server.service.UserService;
import com.quiz.server.service.VerificationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.quiz.server.exception.ExceptionCode.E001;
import static com.quiz.server.exception.ExceptionCode.E002;
import static com.quiz.server.exception.ExceptionCode.E003;
import static com.quiz.server.exception.ExceptionCode.E009;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;

    @Override
    @Transactional
    public User createUser(String username, String password, String email) {
        if (userRepository.existsByUsername(username)) {
            Optional<User> existingUser = userRepository.findByUsername(username);
            handleExistingUser(existingUser, username);
        }

        else if (userRepository.existsByEmail(email)) {
            Optional<User> existingUser = userRepository.findByEmail(email);
            handleExistingUser(existingUser, username);
        }

        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(regex))
            throw new QRuntimeException(E003);

        String encryptedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .username(username)
                .email(email)
                .verified(false)
                .passwordHash(encryptedPassword)
                .build();

        userRepository.save(user);

        return user;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new QRuntimeException(E001)));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new QRuntimeException(E001)));
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new QRuntimeException(E001)));
    }

    @Override
    @Transactional
    public void changePassword(String token, String newPassword, String confirmNewPassword) {
        if (!confirmNewPassword.equals(newPassword))
            throw new QRuntimeException(E009);

        String email = verificationService.getEmailByToken(token);
        Optional<User> user = getUserByEmail(email);

        if (user.isPresent()) {
            User userEntity = user.get();
            userEntity.setPasswordHash(passwordEncoder.encode(newPassword));
            userRepository.save(userEntity);
        }
    }

    private void handleExistingUser(Optional<User> existingUser, String value) {
        existingUser.ifPresent(user -> {
            if (user.getVerified()) {
                throw new QRuntimeException(E002);
            } else {
                userRepository.deleteByUsername(value);
                userRepository.deleteByEmail(value);
            }
        });
    }
}
