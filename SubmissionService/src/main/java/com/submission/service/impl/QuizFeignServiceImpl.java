package com.submission.service.impl;

import org.springframework.stereotype.Service;

import com.quiz.common.view.QuizView;
import com.submission.fign.services.QuizFeignService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizFeignServiceImpl {

    private final QuizFeignService quizFeignService;

    @CircuitBreaker(name = "quiz-service", fallbackMethod = "quizFallback")
    public QuizView getQuizById(Long id) {
        return quizFeignService.getQuizById(id);
    }

    public QuizView quizFallback(Long id, Throwable ex) {
    	System.err.println(ex.getLocalizedMessage());
        return new QuizView();
    }
}
