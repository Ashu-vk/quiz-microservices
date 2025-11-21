package com.quiz.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfigReactive {
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
	 @Order(Ordered.HIGHEST_PRECEDENCE)                                                      
	    @Bean
	    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
	        http
         .csrf(csrf -> csrf.disable())
         .authorizeExchange(exchanges -> exchanges
             .anyExchange().authenticated()
         )
         .oauth2ResourceServer(oauth2 -> oauth2
             .jwt(jwt -> jwt.jwtDecoder(jwtDecoder()))
         );
     
     return http.build();
	    }
    
	 @Bean
	 public ReactiveJwtDecoder jwtDecoder() {
	     SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

	     NimbusReactiveJwtDecoder delegate = NimbusReactiveJwtDecoder
	         .withSecretKey(key)
	         .macAlgorithm(MacAlgorithm.HS512)    // Force HS512 if you sign with HS512
	         .build();
	     return delegate;
	 }
}
