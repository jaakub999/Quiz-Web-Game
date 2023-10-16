package com.quiz.server.model.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "public_question_sets")
public class PublicQuestionSet {
    @Id
    @Column(name = "questionset_key_id")
    private String questionSetKeyId;

    @Column(name = "questionset_name")
    private String questionSetName;

    @Column(name = "user_username")
    private String userUsername;
}
