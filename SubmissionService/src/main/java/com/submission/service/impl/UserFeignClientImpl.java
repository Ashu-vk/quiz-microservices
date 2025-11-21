package com.submission.service.impl;

import org.springframework.stereotype.Service;

import com.quiz.common.view.UserView;
import com.submission.fign.services.UserFeignClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFeignClientImpl {

	   private final UserFeignClient userFeignService;

	    @CircuitBreaker(name = "user-service", fallbackMethod = "userFallback")
	    public UserView getUserById(Long id) {
	        return userFeignService.getUserById(id);
	    }

	    public UserView userFallback(Long id, Throwable ex) {
	    	System.err.println(ex.getLocalizedMessage());
	        return new UserView();
	    }
}
