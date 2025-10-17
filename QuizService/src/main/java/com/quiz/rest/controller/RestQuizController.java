package com.quiz.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.quiz.common.view.QuestionView;
import com.quiz.common.view.QuizView;
import com.quiz.common.view.SubmissionView;
import com.quiz.service.QuizService;

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
	@PostMapping("/quiz/{id}/start")
	public QuizView startQuiz(@PathVariable(value ="id") Long id,
		@RequestParam(value = "userId") Long userId) {
		QuizView view = quizService.getQuizById(id);
		List<QuestionView> qList= webClient.get().uri("http://question-service/questions/quiz/{id}", id)
        .retrieve()
        .bodyToFlux(QuestionView.class)
        .collectList()        // ✅ aggregate into a List
        .block();
		view.setQuestions(qList);
		CompletableFuture<SubmissionView> submissionFuture = webClient.post().uri("http://submission-service/submission/start/quiz?userId={userId}", userId)
				.bodyValue(view)
		.retrieve().bodyToMono(SubmissionView.class).toFuture();
		QuizView quiz =quizService.startQuiz(view, submissionFuture);
		return quiz;
	}
	
	@PostMapping("/quiz/{id}/submit")
	public QuizView submitQuiz(@PathVariable(value ="id") Long id,
		@RequestParam(value = "userId") Long userId) {
		QuizView view = quizService.getQuizById(id);
//		List<QuestionView> qList= webClient.get().uri("http://question-service/questions/quiz/{id}", id)
//        .retrieve()
//        .bodyToFlux(QuestionView.class)
//        .collectList() 
//        .block();
//		view.setQuestions(qList);
		CompletableFuture<SubmissionView> submissionFuture = webClient.post().uri("http://submission-service/submission/submit/quiz?userId={userId}", userId)
				.bodyValue(view)
		.retrieve().bodyToMono(SubmissionView.class).toFuture();
		QuizView quiz =quizService.startQuiz(view, submissionFuture);
//		webClient.get().uri("http://submission-service/submission/submit/quiz?userId={userId}", userId);
		return quiz;
	}
	
	@GetMapping("/quiz/{id}")
	public QuizView getQuiz(@PathVariable(value = "id") Long id) {
		QuizView quiz= quizService.getQuizById(id);
		List<QuestionView> qList= webClient.get().uri("http://question-service/questions/quiz/{id}", id)
        .retrieve()
        .bodyToFlux(QuestionView.class)
        .collectList()        // ✅ aggregate into a List
        .block();
		quiz.setQuestions(qList);
		return quiz;
	}
	
	@GetMapping("/quiz")
	public List<QuizView> getQuiz() {
		return quizService.getQuiz();
	}
	
	
}
