package com.submission.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.common.view.SubmissionView;
import com.submission.fign.services.QuestionFiegnService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionFiegnServiceImpl {
	private final QuestionFiegnService questionService;
	
	@CircuitBreaker(name= "question-service", fallbackMethod = "questionServiceFallback")
	ResponseEntity<SubmissionView> sendSubmissionstoVerify(SubmissionView submission){
		return questionService.sendSubmissionstoVerify(submission);
	}

	ResponseEntity<SubmissionView> questionServiceFallback(SubmissionView submission){
		return new ResponseEntity<SubmissionView>(null);
	}
	
}
