package com.result.view;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultView {

	    private Long id;

	    private Long userId;   // user who attempted the quiz
	    private Long quizId;   // which quiz this result belongs to

	    private Integer score; // final score
	    private Integer totalQuestions;
	    private Integer correctAnswers;

	    private Date attemptedAt = new Date();
}
