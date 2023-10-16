package com.quiz.server.repository;

import com.quiz.server.model.entity.QuestionSet;
import com.quiz.server.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionSetRepository extends JpaRepository<QuestionSet, Long> {

    List<QuestionSet> findByUser(User user);

    Optional<QuestionSet> findByKeyId(String keyId);

    boolean existsByKeyId(String keyId);

    void deleteByKeyId(String keyId);
}
