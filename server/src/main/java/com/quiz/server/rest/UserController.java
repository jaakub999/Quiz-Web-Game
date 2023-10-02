package com.quiz.server.rest;

import com.quiz.server.model.dto.UserDTO;
import com.quiz.server.model.entity.User;
import com.quiz.server.request.ChangePasswordRequest;
import com.quiz.server.service.UserService;
import com.quiz.server.service.EmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.quiz.server.helper.EmailType.REGISTER;
import static org.springframework.http.HttpStatus.ACCEPTED;

@AllArgsConstructor
@RestController
@RequestMapping("${apiPrefix}/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/register")
    @ResponseStatus(ACCEPTED)
    public void registerUser(@RequestBody UserDTO dto) {
        User user = userService.createUser(dto.getUsername(),
                dto.getPassword(),
                dto.getEmail());
        emailService.sendEmail(user, REGISTER);
    }

    @PostMapping("/change-forgotten-password")
    public void changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        String token = request.getToken();
        String newPassword = request.getNewPassword();
        String confirmNewPassword = request.getConfirmNewPassword();
        userService.changePassword(token, newPassword, confirmNewPassword);
    }
}
