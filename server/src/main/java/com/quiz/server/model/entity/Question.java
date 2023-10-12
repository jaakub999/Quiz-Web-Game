package com.quiz.server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "points", nullable = false)
    private Integer points;

    @Lob
    @Column(name = "image")
    private String image;

    @Column(name = "image_name")
    private String imageName;

    @Size(max = 4)
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "question_set_id")
    private QuestionSet questionSet;
}
