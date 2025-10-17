package com.question.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.question.services.QuestionService;
import com.quiz.common.view.QuestionView;
import com.quiz.common.view.SubmissionView;

@RestController
public class RestQuestionController {

	@Autowired
	private QuestionService questionService;

	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public List<QuestionView> getQuestions() {
		return questionService.getAll();

	}

	@RequestMapping(value = "/questions/quiz/{quizId}", method = RequestMethod.GET)
	public List<QuestionView> getQuestionsByQuizId(@PathVariable("quizId") Long quizId) {
		return questionService.getAllByQuizId(quizId);

	}
	
	@RequestMapping(value = "/questions/{id}", method = RequestMethod.GET)
	public QuestionView getQuestionsById(@PathVariable("id") Long id) {
		return questionService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No question found by id" + id));
		
	}

	@RequestMapping(value = "/questions", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public QuestionView saveOrUpdate(@RequestBody QuestionView questionView) {
		return questionService.saveOrUpdateQuestion(questionView);

	}
	
	@RequestMapping(value = "/questions/quiz/{quizId}/add",
					method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	public QuestionView saveOrUpdateForQuiz(@PathVariable(value = "quizId") Long quizId,
											@RequestBody QuestionView questionView) {
		return questionService.saveOrUpdateQuestionForQuiz(quizId, questionView);
		
	}
	
	@PostMapping("/questions/submission")
	public SubmissionView verifyAnwers(@RequestBody SubmissionView submission) throws Exception {
		
		return questionService.verifySubmission(submission);
	}

}
