package com.user.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;

/**
 * For customize the rate limiter used for login for per user
 */
@Configuration
public class MyRateLimiterConfig {
	
	   private int limitForPeriod;
	    private Duration limitRefreshPeriod;
	    private Duration timeoutDuration;
	
	@Bean
	public RateLimiterRegistry rateLimiterRegistry() {
	    return RateLimiterRegistry.ofDefaults();
	}

	  @Autowired
	    private RateLimiterRegistry rateLimiterRegistry;

	   
//	    public RateLimiter rateLimiterFor(String key) {
//	        return rateLimiterRegistry.rateLimiter(key, () -> {
//	            RateLimiterConfig.custom()
//	                .limitForPeriod(limitForPeriod)
//	                .limitRefreshPeriod(limitRefreshPeriod)
//	                .timeoutDuration(timeoutDuration)
//	                .build()
//	        } );
//	    }
}
