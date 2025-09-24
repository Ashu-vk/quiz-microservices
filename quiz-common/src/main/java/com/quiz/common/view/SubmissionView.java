package com.quiz.common.view;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionView {
    private Long id;
    private QuizView quiz;     // from Quiz Service
    private UserView user;     // from User Service
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;
    private Integer totalPoints;
    private String status;   // e.g., "IN_PROGRESS", "COMPLETED", "GRADED"
}
