package com.quiz.config;

import java.util.Optional;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

//@Configuration
//public class WebClientConfigMain {
//
//    @Bean
//    @LoadBalanced
//    public WebClient.Builder webClientBuilder() {
//        return WebClient.builder();
//    }
//
//    // Log filter
//    ExchangeFilterFunction logRequest = ExchangeFilterFunction.ofRequestProcessor(request -> {
//        System.out.println("Request: " + request.method() + " " + request.url());
//        request.headers().forEach((name, values) -> 
//            System.out.println(name + ": " + values)
//        );
//        return Mono.just(request);
//    });
//
//    // Header propagation filter
//    ExchangeFilterFunction headerPropagationFilter = (request, next) -> {
//        // Get current request context
//        ServletRequestAttributes attributes = 
//            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        
//        if (attributes != null) {
//            HttpServletRequest httpRequest = attributes.getRequest();
//            
//            // Get headers from incoming request
//            String authUser = httpRequest.getHeader("X-Auth-User");
//            String authUserId = httpRequest.getHeader("X-Auth-UserId");
//            String authEmail = httpRequest.getHeader("X-Auth-Email");
//            String authRoles = httpRequest.getHeader("X-Auth-Roles");
//            String authorization = httpRequest.getHeader("Authorization");
//            
//            // Build new request with propagated headers
//            ClientRequest newRequest = ClientRequest.from(request)
//                .headers(headers -> {
//                    if (authorization != null) {
//                        headers.set("Authorization", authorization);
//                    }
//                    if (authUser != null) {
//                        headers.set("X-Auth-User", authUser);
//                    }
//                    if (authUserId != null) {
//                        headers.set("X-Auth-UserId", authUserId);
//                    }
//                    if (authEmail != null) {
//                        headers.set("X-Auth-Email", authEmail);
//                    }
//                    if (authRoles != null) {
//                        headers.set("X-Auth-Roles", authRoles);
//                    }
//                })
//                .build();
//            
//            return next.exchange(newRequest);
//        }
//        
//        return next.exchange(request);
//    };
//
//    @Bean
//    public WebClient webClient(WebClient.Builder webClientBuilder) {
//        return webClientBuilder
//                .filter(logRequest)
//                .filter(headerPropagationFilter)
//                .build();
//    }
//}

@Configuration
public class WebClientConfigMain {
	
	@Bean
	@LoadBalanced
	public WebClient.Builder webClientBuilder() {
	    return WebClient.builder();
	}
	
	    @Bean
	    public WebClient webClient() {
	    	
	        return webClientBuilder()
	        		.filter(jwtFilter)
	        		.filter(logRequest)
	        		.build();
	    }
	    
	    ExchangeFilterFunction logRequest = ExchangeFilterFunction.ofRequestProcessor(request -> {
	        System.err.println("Request: " + request.method() + " " + request.url()+" " + request.headers());
	        System.out.println(request.headers().asSingleValueMap().toString());
	        return Mono.just(request);
	    });
	    
//  Reactive way	    
	    ExchangeFilterFunction jwtFilter = (request, next) -> ReactiveSecurityContextHolder.getContext()
        		.map(ctx -> ctx.getAuthentication()).cast(JwtAuthenticationToken.class).map(auth->auth.getToken().getTokenValue())
        		.defaultIfEmpty("").flatMap(token-> {
        			ClientRequest newRequest = ClientRequest.from(request).headers(headers->headers.setBearerAuth(token)).build();
        			return next.exchange(newRequest);
        		});
	    
	    
//	    ExchangeFilterFunction jwtFilter = (request, next) -> {
//
//	        String token = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
//	                .filter(JwtAuthenticationToken.class::isInstance)
//	                .map(JwtAuthenticationToken.class::cast)
//	                .map(jwt -> jwt.getToken().getTokenValue())
//	                .orElse("");
//
//	        ClientRequest newRequest = ClientRequest.from(request)
//	                .headers(h -> h.setBearerAuth(token))
//	                .build();
//
//	        return next.exchange(newRequest);
//	    };
}
