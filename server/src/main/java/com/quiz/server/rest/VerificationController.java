package com.quiz.server.rest;

import com.quiz.server.model.entity.User;
import com.quiz.server.properties.AppProperties;
import com.quiz.server.service.EmailService;
import com.quiz.server.service.UserService;
import com.quiz.server.service.VerificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static com.quiz.server.helper.EmailType.PASSWORD;
import static com.quiz.server.helper.EmailType.REGISTER;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.FOUND;

@AllArgsConstructor
@RestController
@RequestMapping("${apiPrefix}/email")
public class VerificationController {

    private final VerificationService verificationService;
    private final UserService userService;
    private final EmailService emailService;
    private final AppProperties appProperties;

    @GetMapping("/register-email")
    public ResponseEntity<String> verifyRegisterEmail(@RequestParam("token") String token) {
        String locationUrl = appProperties.getVerification().getLocation();

        try {
            verificationService.verifyToken(token, REGISTER);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", locationUrl);
            return new ResponseEntity<>(headers, FOUND);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/password-email")
    public ResponseEntity<String> verifyPasswordEmail(@RequestParam("token") String token) {
        String locationUrl = appProperties.getUserPassword().getLocation();

        try {
            verificationService.verifyToken(token, PASSWORD);
            URI redirectUri = UriComponentsBuilder.fromUriString(locationUrl + token).build().toUri();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", redirectUri.toString());
            return new ResponseEntity<>(headers, FOUND);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/resend-register-email")
    @ResponseStatus(ACCEPTED)
    public void resendRegisterEmail(@RequestParam("email") String email) {
        Optional<User> user = userService.getUserByEmail(email);
        user.ifPresent(u -> emailService.sendEmail(u, REGISTER));
    }

    @PostMapping("/send-password-email")
    @ResponseStatus(ACCEPTED)
    public void sendPasswordEmail(@RequestParam("email") String email) {
        Optional<User> user = userService.getUserByEmail(email);
        user.ifPresent(u -> emailService.sendEmail(u, PASSWORD));
    }
}
