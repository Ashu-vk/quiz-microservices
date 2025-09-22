package com.question.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.question.services.QuestionService;
import com.question.view.QuestionView;

@RestController
public class RestQuestionController {

	@Autowired
	private QuestionService questionService;

	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public List<QuestionView> getQuestions() {
		return questionService.getAll();

	}

	@RequestMapping(value = "/questions/{quizId}", method = RequestMethod.GET)
	public List<QuestionView> getQuestionsByQuizId(@PathVariable("quizId") Long quizId) {
		System.err.println(quizId);
		return questionService.getAllByQuizId(quizId);

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

}
