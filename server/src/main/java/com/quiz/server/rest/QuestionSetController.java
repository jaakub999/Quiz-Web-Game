package com.quiz.server.rest;

import com.quiz.server.model.dto.QuestionSetDTO;
import com.quiz.server.response.QuestionSetResponse;
import com.quiz.server.service.QuestionSetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.quiz.server.security.SecurityConstants.HEADER;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    public ResponseEntity<List<QuestionSetResponse>> getUserQuestionSets(@RequestHeader(HEADER) String token) {
        return ResponseEntity.ok(questionSetService.getUserQuestionSets(token));
    }

    @GetMapping("/{key-id}")
    public ResponseEntity<?> getQuestionSet(@PathVariable("key-id") String keyId) {
        Optional<QuestionSetDTO> dto = questionSetService.getQuestionSet(keyId);

        if (dto.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body("Question set not found");

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{key-id}")
    public void deleteQuestionSet(@PathVariable("key-id") String keyId) {
        questionSetService.deleteQuestionSet(keyId);
    }

    @PutMapping("/edit")
    public void editQuestionSet(@RequestBody QuestionSetDTO dto) {
        questionSetService.editQuestionSet(dto);
    }
}
