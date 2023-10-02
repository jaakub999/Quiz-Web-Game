package com.quiz.server.rest;

import com.quiz.server.model.entity.User;
import com.quiz.server.request.AuthRequest;
import com.quiz.server.response.AuthResponse;
import com.quiz.server.security.JwtTokenProvider;
import com.quiz.server.service.AuthenticationService;
import com.quiz.server.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.quiz.server.security.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@AllArgsConstructor
@RestController
@RequestMapping("${apiPrefix}/login")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        String username = request.getUsername();
        String password = URLDecoder.decode(request.getPassword(), StandardCharsets.UTF_8);
        boolean keepLogged = request.isKeepLogged();
        Optional<User> user = userService.getUserByUsername(username);

        if (user.isEmpty() || !authService.authenticate(username, password)) {
            return ResponseEntity.status(UNAUTHORIZED).body("Invalid username or password");
        }

        if (!authService.isUserVerified(username)) {
            return ResponseEntity.status(FORBIDDEN).body("User is not verified");
        }

        String token = jwtTokenProvider.generateToken(user.get(), keepLogged);
        return ResponseEntity.ok()
                .header(AUTHORIZATION, TOKEN_PREFIX + token)
                .body(new AuthResponse(token));
    }
}
