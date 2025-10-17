package com.submission.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.common.view.SubmissionAnswerView;
import com.submission.service.SubmissionAnswerService;

@RestController
public class RestSubmitAnswerController {

	@Autowired
	private SubmissionAnswerService submissionService;

	@RequestMapping(value = "/submitAnswer", method = RequestMethod.GET, produces = "application/json")
	public List<SubmissionAnswerView> getAllSubAnswers(){
		return submissionService.getAll();
	}
	
//	@RequestMapping(value = "/submitAnswer/{quizId}/users/{userId}", method = RequestMethod.GET, produces = "application/json")
//	public List<SubmissionAnswerView> getAllSubAnswersOfQuizByUser(@PathVariable(value = "quizId") Long quizId,
//														@PathVariable(value = "userId") Long userId){
//		return submissionService.getAllByQuizAndUser(quizId, userId);
//	}
	
	@RequestMapping(value = "submission/{sid}/submitAnswer", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public SubmissionAnswerView submitAnswer(@PathVariable(value = "sid") Long submisssionId,
										  @RequestBody SubmissionAnswerView submissionAnswerView) throws Exception{
		return submissionService.saveOrUpdateBySubmission(submisssionId, submissionAnswerView).orElseThrow();
	}
	
	@RequestMapping(value = "/submitAnswer/{sId}", method = RequestMethod.GET, produces = "application/json")
	public SubmissionAnswerView getUsers(@PathVariable(value = "sId") Long submitAnswerId){
		return submissionService.getById(submitAnswerId).orElseThrow();
	}
}
