package com.question.services;

import java.util.List;
import java.util.Optional;

import com.question.view.QuestionView;

public interface QuestionService {

	Optional<QuestionView> getById(Long id);

	List<QuestionView> getAll();

	List<QuestionView> getAllByQuizId(Long quizId);

	QuestionView saveOrUpdateQuestion(QuestionView view);

	QuestionView saveOrUpdateQuestionForQuiz(Long quizId, QuestionView questionView);

}
