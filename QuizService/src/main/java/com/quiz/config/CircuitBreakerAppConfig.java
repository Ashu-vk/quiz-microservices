package com.quiz.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import jakarta.persistence.EntityNotFoundException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Configuration
public class CircuitBreakerAppConfig {

//	CircuitBreakerRegistry circuitBreakerRegistry = 
//			  CircuitBreakerRegistry.ofDefaults();
	
	// Create a custom configuration for a CircuitBreaker
	@Bean
	CircuitBreakerConfig circuitBreakerConfig() {
		return CircuitBreakerConfig.custom()
	  .failureRateThreshold(50)
	  .slowCallRateThreshold(50)
	  .waitDurationInOpenState(Duration.ofMillis(1000))
	  .slowCallDurationThreshold(Duration.ofSeconds(2))
	  .permittedNumberOfCallsInHalfOpenState(3)
	  .minimumNumberOfCalls(10)
	  .slidingWindowType(SlidingWindowType.TIME_BASED)
	  .automaticTransitionFromOpenToHalfOpenEnabled(true)
	  .slidingWindowSize(5)
	  .ignoreExceptions(EntityNotFoundException.class)
//	  .recordException(e -> INTERNAL_SERVER_ERROR
//	                 .equals(getResponse().getStatus()))
//	  .recordExceptions(IOException.class, TimeoutException.class)
//	  .ignoreExceptions(BusinessException.class, OtherBusinessException.class)
	  .build();
	}

	// Create a CircuitBreakerRegistry with a custom global configuration
	@Bean
	CircuitBreakerRegistry circuitBreakerRegistry (CircuitBreakerConfig config) {
	  return CircuitBreakerRegistry.of(config);
	}

	// Get or create a CircuitBreaker from the CircuitBreakerRegistry 
	// with the global default configuration
//	CircuitBreaker circuitBreakerWithDefaultConfig = 
//	  circuitBreakerRegistry.circuitBreaker("start-quiz");
//
//	// Get or create a CircuitBreaker from the CircuitBreakerRegistry 
//	// with a custom configuration
//	CircuitBreaker circuitBreakerWithCustomConfig = circuitBreakerRegistry
//			  .circuitBreaker("name2", circuitBreakerConfig);
}
