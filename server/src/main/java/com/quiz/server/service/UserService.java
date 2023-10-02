package com.quiz.server.service;

import com.quiz.server.model.entity.User;

import java.util.Optional;

public interface UserService {

    User createUser(String username, String password, String email);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Long id);

    void changePassword(String token, String newPassword, String confirmNewPassword);
}
