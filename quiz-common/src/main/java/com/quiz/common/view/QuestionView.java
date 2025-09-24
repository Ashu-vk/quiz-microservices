package com.quiz.common.view;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionView {
	    private Long id;
	    private QuizView quiz; // ID from Quiz Service
	    private String text; // The actual question
	    private List<String> options; // Multiple-choice options
	    private String correctAnswer; // Could also be an index (e.g., "A", "1")
	    private String explanation; // Optional: Explain the correct answer
	    private String type; // "MCQ", "TrueFalse", "ShortAnswer", etc.
	    private Integer points; // Score for this question
	    private Integer timeLimitInSeconds; // Optional: time per question
	    private Boolean isActive; // For soft delete or visibility
	    private String difficulty; // "Easy", "Medium", "Hard"
	    private String category; // "Math", "History", etc.
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
}
