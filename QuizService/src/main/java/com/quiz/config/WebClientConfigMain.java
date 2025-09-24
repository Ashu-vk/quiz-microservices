package com.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfigMain {
	
	    
	    @Bean
	    public WebClient webClient(WebClient.Builder builder) {
	        return builder
	        		.filter(logRequest)
	        		.build();
	    }
	    
	    ExchangeFilterFunction logRequest = ExchangeFilterFunction.ofRequestProcessor(request -> {
	        System.err.println("Request: " + request.method() + " " + request.url());
	        return Mono.just(request);
	    });

}
