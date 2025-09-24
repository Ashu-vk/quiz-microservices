package com.submission.service;

import java.util.List;
import java.util.Optional;

import com.quiz.common.view.QuizView;
import com.quiz.common.view.SubmissionView;
import com.quiz.common.view.UserView;


public interface SubmissionService {

	List<SubmissionView> getSubmissions();

	List<SubmissionView> getSubmissionsByQuiz(Long quizId);

	List<SubmissionView> getSubmissionsByUser(Long userId);

	Optional<SubmissionView> getSubmissionById(Long id);

	Optional<SubmissionView> saveOrUpdateSubmission(SubmissionView view);

	SubmissionView startQuiz(QuizView quiz, UserView userView);

}
