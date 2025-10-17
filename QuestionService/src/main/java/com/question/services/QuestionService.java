package com.question.services;

import java.util.List;
import java.util.Optional;

import com.quiz.common.view.QuestionView;
import com.quiz.common.view.SubmissionView;


public interface QuestionService {

	Optional<QuestionView> getById(Long id);

	List<QuestionView> getAll();

	List<QuestionView> getAllByQuizId(Long quizId);

	QuestionView saveOrUpdateQuestion(QuestionView view);

	QuestionView saveOrUpdateQuestionForQuiz(Long quizId, QuestionView questionView);

	SubmissionView verifySubmission(SubmissionView submission) throws Exception;

}
