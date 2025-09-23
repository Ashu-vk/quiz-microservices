package com.submission.service;

import java.util.List;
import java.util.Optional;

import com.submission.view.SubmissionAnswerView;

public interface SubmissionAnswerService {

	Optional<SubmissionAnswerView> getById(Long sId);

	List<SubmissionAnswerView> getAll();

//	Optional<SubmissionAnswerView> saveOrUpdate(SubmissionAnswerView view);

	List<SubmissionAnswerView> getAllByQuizAndUser(Long quizId, Long userId);

	Optional<SubmissionAnswerView> saveOrUpdate(Long quizId, Long userId, SubmissionAnswerView view);

}
