package com.submission.view;

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

    private Long quizId;     // from Quiz Service
    private Long userId;     // from User Service
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;
    private Integer totalPoints;
    private String status;   // e.g., "IN_PROGRESS", "COMPLETED", "GRADED"
}
