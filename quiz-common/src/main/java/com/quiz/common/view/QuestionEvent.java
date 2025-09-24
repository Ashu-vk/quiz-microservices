package com.quiz.common.view;

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
	 private QuizView quiz;
	 private QuestionView question;
	 private String content;
	 private LocalDateTime timestamp;
}
