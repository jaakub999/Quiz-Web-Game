package com.quiz.server.repository;

import com.quiz.server.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    boolean existsByCode(String code);

    Optional<Session> findByCode(String code);
}
