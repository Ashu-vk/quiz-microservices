package com.quiz.view;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionEvent {
	 private Long quizId;
	    private Long questionId;
	    private String content;
	    private LocalDateTime timestamp;
}
