package com.submission.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.common.view.SubmissionAnswerView;
import com.quiz.common.view.SubmissionView;
import com.submission.model.Submission;
import com.submission.model.SubmissionAnswer;
import com.submission.repository.SubmissionAnswerRepo;
import com.submission.service.SubmissionAnswerService;
import com.submission.service.SubmissionService;

@Service
public class SubmissionAnswerServiceImpl implements SubmissionAnswerService{
	
	@Autowired
	private SubmissionAnswerRepo repository;
	
	@Autowired
	private CommonSubmissionService commonService;
	
	
	@Override
	public Optional<SubmissionAnswerView> getById(Long sId){
		if (sId == null) {
			return Optional.empty();
		}
		return commonService.toSubmissionAnswerView(repository.findById(sId).orElse(null));
	}
	
	@Override
	public List<SubmissionAnswerView> getAll(){
		return commonService.toAnswerViewList(repository.findAll());
	}

	private Optional<SubmissionAnswerView> saveAnswer(SubmissionAnswer answer) {
		return commonService.toSubmissionAnswerView(repository.save(answer));
	}
	
	@Override
	public Optional<SubmissionAnswerView> saveOrUpdateBySubmission(Long submissionId, SubmissionAnswerView view) throws Exception {
		commonService.validateAnswerView(view, false);
		commonService.validateSubmissionStatus(submissionId);
		SubmissionAnswer subAnswer = repository.findByQuestionIdAndSubmissionId(view.getQuestion().getId(), submissionId).orElse(commonService.toSubmissionAnswer(view));
		subAnswer.setSubmissionId(submissionId);
		subAnswer.setGivenAnswer(view.getGivenAnswer());
		return saveAnswer(subAnswer);
		
	}
    
	@Override
	public List<SubmissionAnswerView> getAllBySubmission(Long submissionId){
		return commonService.toAnswerViewList(repository.findBySubmissionId(submissionId));
	}

}
