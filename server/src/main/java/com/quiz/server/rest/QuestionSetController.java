package com.quiz.server.rest;

import com.quiz.server.model.dto.QuestionSetDTO;
import com.quiz.server.service.QuestionSetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.quiz.server.security.SecurityConstants.HEADER;

@AllArgsConstructor
@RestController
@RequestMapping("${apiPrefix}/question-set")
public class QuestionSetController {

    private final QuestionSetService questionSetService;

    @PostMapping("/create")
    public void createQuestionSet(@RequestHeader(HEADER) String token,
                                  @RequestBody QuestionSetDTO dto) {
        questionSetService.createQuestionSet(token, dto);
    }

    @GetMapping("/user-sets")
    public ResponseEntity<List<QuestionSetDTO>> getUserQuestionSets(@RequestHeader(HEADER) String token) {
        return ResponseEntity.ok(questionSetService.getUserQuestionSets(token));
    }
}
