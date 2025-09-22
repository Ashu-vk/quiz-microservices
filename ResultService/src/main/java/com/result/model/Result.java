package com.result.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "results")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;   // user who attempted the quiz
    private Long quizId;   // which quiz this result belongs to

    private Integer score; // final score
    private Integer totalQuestions;
    private Integer correctAnswers;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date attemptedAt = new java.util.Date();
}

