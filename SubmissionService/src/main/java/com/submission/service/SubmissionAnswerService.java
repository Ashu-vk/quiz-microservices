package com.submission.service;

import java.util.List;
import java.util.Optional;

import com.quiz.common.view.SubmissionAnswerView;


public interface SubmissionAnswerService {

	Optional<SubmissionAnswerView> getById(Long sId);

	List<SubmissionAnswerView> getAll();

//	Optional<SubmissionAnswerView> saveOrUpdate(SubmissionAnswerView view);


	Optional<SubmissionAnswerView> saveOrUpdateBySubmission(Long submisssionId, SubmissionAnswerView view) throws Exception;

	List<SubmissionAnswerView> getAllBySubmission(Long submissionId);

//	List<SubmissionAnswerView> saveAll(List<SubmissionAnswerView> viewList) throws Exception;

}
