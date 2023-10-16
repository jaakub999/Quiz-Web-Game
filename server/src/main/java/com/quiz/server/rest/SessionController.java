package com.quiz.server.rest;

import com.quiz.server.helper.Mode;
import com.quiz.server.model.dto.SessionDTO;
import com.quiz.server.model.entity.User;
import com.quiz.server.response.CodeResponse;
import com.quiz.server.response.SessionResponse;
import com.quiz.server.service.AuthenticationService;
import com.quiz.server.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.quiz.server.security.SecurityConstants.HEADER;

@AllArgsConstructor
@RestController
@RequestMapping("${apiPrefix}/session")
public class SessionController {

    private final SessionService sessionService;
    private final AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<CodeResponse> createSession(@RequestHeader(HEADER) String token,
                                                @RequestBody Mode mode) {
        return ResponseEntity.ok(new CodeResponse(sessionService.createSession(token, mode)));
    }

    @GetMapping("/{code}")
    public ResponseEntity<SessionResponse> getSession(@RequestHeader(HEADER) String token,
                                                      @PathVariable String code) {
        User user = authenticationService.getUserByToken(token);
        SessionDTO dto = sessionService.getSession(code);
        return ResponseEntity.ok(new SessionResponse(dto, user.getUsername()));
    }

    @PutMapping("/join")
    public void joinSession(@RequestHeader(HEADER) String token,
                            @RequestBody String code) {
        sessionService.joinSession(token, code);
    }
}
