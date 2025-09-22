package com.submission.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quizId;     // from Quiz Service
    private Long userId;     // from User Service
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;
    private Integer totalPoints;
    private String status;   // e.g., "IN_PROGRESS", "COMPLETED", "GRADED"
}
