package com.quiz.service;

import java.util.List;

import com.quiz.model.Quiz;
import com.quiz.view.QuizView;

public interface QuizService {

	QuizView saveQuiz(QuizView view);

	QuizView getQuizById(Long id);

	List<QuizView> getQuiz();

}
