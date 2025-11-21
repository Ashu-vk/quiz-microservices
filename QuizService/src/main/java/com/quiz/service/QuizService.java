package com.quiz.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.quiz.common.view.QuizView;
import com.quiz.common.view.SubmissionView;

public interface QuizService {

	QuizView saveQuiz(QuizView view);

	QuizView getQuizById(Long id);

	List<QuizView> getQuiz();

	QuizView startQuiz(QuizView view, SubmissionView submissionFuture);

	QuizView submitQuiz(QuizView view, CompletableFuture<SubmissionView> submissionFuture);

}
