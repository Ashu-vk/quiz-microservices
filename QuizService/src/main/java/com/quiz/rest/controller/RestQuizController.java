package com.quiz.rest.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.quiz.common.view.QuestionView;
import com.quiz.common.view.QuizView;
import com.quiz.common.view.SubmissionView;
import com.quiz.service.QuizService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class RestQuizController {
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private WebClient webClient;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/quiz")
	public QuizView saveQuiz(@RequestBody QuizView quiz) {
		return quizService.saveQuiz(quiz);
	}
	
	
	@PostMapping("/quiz/{id}/start")
	@CircuitBreaker(name = "start-quiz", fallbackMethod = "onStartQuizFailure")
	@Bulkhead(name= "start-quiz-bulkhead", fallbackMethod = "onStartQuizFailure")
	public Mono<QuizView> startQuiz(@PathVariable(value ="id") Long id,
		@RequestParam(value = "userId") Long userId) throws InterruptedException {
		    
		Thread.sleep(5000);
		    // Step 1: fetch quiz from JPA (blocking)
		    Mono<QuizView> quizMono = Mono.fromCallable(() -> quizService.getQuizById(id))
		            .subscribeOn(Schedulers.boundedElastic());

		    // Step 2: fetch questions from Question Service (reactive)
		    Mono<List<QuestionView>> questionsMono =
		            webClient.get()
		                    .uri("http://question-service/questions/quiz/{id}", id)
		                    .retrieve()
		                    .bodyToFlux(QuestionView.class)
		                    .collectList();

		    // Step 3: Combine both
		    return quizMono.zipWith(questionsMono)
		            .flatMap(tuple -> {
		                QuizView quiz = tuple.getT1();
		                List<QuestionView> questions = tuple.getT2();
		                quiz.setQuestions(questions);

		                // Step 4: call submission-service reactively
		                Mono<SubmissionView> submissionMono =
		                        webClient.post()
		                                .uri("http://submission-service/submission/start/quiz?userId={userId}", userId)
		                                .bodyValue(quiz)
		                                .retrieve()
		                                .bodyToMono(SubmissionView.class);

		                // Step 5: call service logic and return QuizView
		                return submissionMono.flatMap(submission -> 
		                    Mono.fromCallable(() -> {
		                        quizService.startQuiz(quiz, submission);
		                        return quiz; // Return the quiz object
		                    }).subscribeOn(Schedulers.boundedElastic())
		                );
		            });
	}
	
	public  Mono<QuizView> onStartQuizFailure(Long id, Long userId, Throwable t) {
	    logger.info("Fallback-1: {}", t.getMessage());
	    return Mono.just(new QuizView());
	}
	public  Mono<QuizView> onBulkheadQuiz(Long id, Long userId, Throwable t) {
		logger.info("Bulkhead-1: {}", t.getMessage());
		return Mono.just(new QuizView());
	}
	
	
	@PostMapping("/quiz/{id}/submit")
	public QuizView submitQuiz(@PathVariable(value ="id") Long id,
		@RequestParam(value = "userId") Long userId) {
		QuizView view = quizService.getQuizById(id);
		CompletableFuture<SubmissionView> submissionFuture = webClient.post().uri("http://submission-service/submission/submit/quiz?userId={userId}", userId)
				.bodyValue(view)
		.retrieve().bodyToMono(SubmissionView.class).toFuture();
		QuizView quiz =quizService.submitQuiz(view, submissionFuture);
//		webClient.get().uri("http://submission-service/submission/submit/quiz?userId={userId}", userId);
		return quiz;
	}
	
	@GetMapping("/quiz/{id}")
	@CircuitBreaker(name = "get-quiz", fallbackMethod = "onFetchQuestionsFailure")
	public Mono<QuizView> getQuiz(@PathVariable(value = "id") Long id) {
	    return Mono.fromCallable(() -> quizService.getQuizById(id))  // JPA call (blocking)
	            .subscribeOn(Schedulers.boundedElastic())            // run blocking call safely
	            .flatMap(quiz -> {
	                if (quiz == null) {
	                    return Mono.empty();
	                }

	                return webClient.get()
	                        .uri("http://question-service/questions/quiz/{id}", id)
	                        .retrieve()
	                        .bodyToFlux(QuestionView.class)
	                        .collectList()
	                        .map(questions -> {
	                            quiz.setQuestions(questions);
	                            return quiz;
	                        });
	            });
	}
	
	public Mono<QuizView> onFetchQuestionsFailure(Long id, Throwable t) {
	    logger.info("Question Service failed:: {}", t.getMessage());
	    return Mono.fromCallable(() -> quizService.getQuizById(id))
	    .subscribeOn(Schedulers.boundedElastic());
	}
	
	@GetMapping("/quiz")
	public List<QuizView> getQuiz(@RequestHeader("X-Auth-User") String username,
            @RequestHeader("X-Auth-Roles") String roles) {
		return quizService.getQuiz();
	}
	
	
}
