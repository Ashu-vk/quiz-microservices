package com.submission.fign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import feign.Logger;
import feign.RequestInterceptor;
//import feign.RequestInterceptor;

@Configuration
public class FeignConfig {
	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
	            requestTemplate.header("Authorization",  "Bearer "+jwtAuth.getToken().getTokenValue());
	        }
	    };
	}
	

    @Bean
    Logger.Level feignLoggerLevel() {
        // FULL: log headers, body, metadata
        return Logger.Level.FULL;
    }
}