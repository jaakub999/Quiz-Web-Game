package com.quiz.server.repository;

import com.quiz.server.model.view.PublicQuestionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicQuestionSetRepository extends JpaRepository<PublicQuestionSet, String> {}