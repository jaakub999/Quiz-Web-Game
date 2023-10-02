package com.quiz.server.repository;

import com.quiz.server.model.entity.QuestionSet;
import com.quiz.server.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionSetRepository extends JpaRepository<QuestionSet, Long> {

    List<QuestionSet> getQuestionSetsByUser(User user);
}
