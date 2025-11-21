package com.submission.fign.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.quiz.common.view.SubmissionView;
import com.submission.fign.config.FeignConfig;

@FeignClient(name = "question-service", configuration = FeignConfig.class)
public interface QuestionFiegnService {
	
	  @PostMapping("/questions/submission")
	    ResponseEntity<SubmissionView> sendSubmissionstoVerify(@RequestBody SubmissionView submission);

}
