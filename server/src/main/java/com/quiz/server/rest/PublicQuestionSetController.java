package com.quiz.server.rest;

import com.quiz.server.model.dto.PublicQuestionSetDTO;
import com.quiz.server.service.PublicQuestionSetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${apiPrefix}/public-question-sets")
public class PublicQuestionSetController {

    private final PublicQuestionSetService publicQuestionSetService;

    @GetMapping
    public ResponseEntity<List<PublicQuestionSetDTO>> getPublicQuestionSets() {
        return ResponseEntity.ok(publicQuestionSetService.getPublicQuestionSets());
    }
}
