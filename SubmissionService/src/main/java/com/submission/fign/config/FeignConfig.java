package com.submission.fign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.RequestInterceptor;

@Configuration
public class FeignConfig {
//	@Bean
//	public RequestInterceptor requestInterceptor() {
//		return requestTemplate -> requestTemplate.header("Authorization", "Bearer TOKEN");
//	}
	

    @Bean
    Logger.Level feignLoggerLevel() {
        // FULL: log headers, body, metadata
        return Logger.Level.FULL;
    }
}