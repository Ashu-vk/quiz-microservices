package com.submission.fign.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.quiz.common.view.QuizView;
import com.submission.fign.config.FeignConfig;

@FeignClient(name = "quiz-service", configuration = FeignConfig.class)
public interface QuizFeignService {
	@GetMapping("/quiz/{id}")
	QuizView getQuizById(@PathVariable("id") Long id);
}
