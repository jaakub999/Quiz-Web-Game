package com.quiz.server.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question_sets")
public class QuestionSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "public_access")
    private Boolean publicAccess;

    @Column(name = "key_id", nullable = false, unique = true)
    private String keyId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "questionSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Question> questions = new ArrayList<>();

    @PrePersist
    private void generateKeyId() {
        this.keyId = "key_" + id;
    }
}
