package com.quiz.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.quiz.service.QuizService;
import com.quiz.view.QuestionView;
import com.quiz.view.QuizView;
import com.quiz.view.UserView;

@RestController
public class RestQuizController {
	@Autowired
	private QuizService quizService;
	@Autowired
	private WebClient webClient;
	
	@PostMapping("/quiz")
	public QuizView saveQuiz(@RequestBody QuizView quiz) {
		return quizService.saveQuiz(quiz);
	}
	@GetMapping("/quiz/{id}")
	public QuizView getQuiz(@PathVariable(value = "id") Long id) {
		QuizView quiz= quizService.getQuizById(id);
		System.err.println(quiz.getTitle());
		System.err.println(quiz.getId());
		List<QuestionView> qList= webClient.get().uri("http://question-service/questions/{id}", id)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<QuestionView>>() {})
        .block();
		System.err.println(qList.size()+"users");
		quiz.setQuestions(qList);
		return quiz;
	}
	@GetMapping("/quiz")
	public List<QuizView> getQuiz() {
		return quizService.getQuiz();
	}
	
	
}
