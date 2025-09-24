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
public class QuizView {

	    private Long id;

	    private String title;

	    private String description;

	    private String category; // e.g., "Science", "Math", "History"

	    private String difficulty; // "Easy", "Medium", "Hard"

	    private Boolean ispublished;

	    private Boolean isTimed;

	    private Integer durationInMinutes; // Total time for the quiz

	    private Integer totalPoints; // Optional: sum of question points

	    private Integer questionCount; // Optional: number of questions

	    private String creator; // User or admin who created the quiz

	    private LocalDateTime startDateTime; // Optional: quiz availability start

	    private LocalDateTime endDateTime;   // Optional: quiz availability end

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;
	    private List<QuestionView> questions;

	}


