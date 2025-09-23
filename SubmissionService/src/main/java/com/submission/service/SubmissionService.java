package com.submission.service;

import java.util.List;
import java.util.Optional;

import com.submission.view.SubmissionView;

public interface SubmissionService {

	List<SubmissionView> getSubmissions();

	List<SubmissionView> getSubmissionsByQuiz(Long quizId);

	List<SubmissionView> getSubmissionsByUser(Long userId);

	Optional<SubmissionView> getSubmissionById(Long id);

	Optional<SubmissionView> saveOrUpdateSubmission(SubmissionView view);

}
