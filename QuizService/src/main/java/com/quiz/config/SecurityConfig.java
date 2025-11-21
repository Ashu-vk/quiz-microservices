package com.quiz.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import io.jsonwebtoken.security.Keys;

//@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
	 @Order(Ordered.HIGHEST_PRECEDENCE)                                                      
	    @Bean
	    SecurityFilterChain apiHttpSecurity(HttpSecurity http) throws Exception {
	        http
         .csrf(csrf -> csrf.disable())
         .authorizeHttpRequests(req->req.anyRequest().authenticated())
         .oauth2ResourceServer(oauth2 -> oauth2
             .jwt(jwt -> jwt.decoder(jwtDecoder()))
         );
     
     return http.build();
	    }
    
	 @Bean
	 public JwtDecoder jwtDecoder() {
	     SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

	     NimbusJwtDecoder delegate = NimbusJwtDecoder
	         .withSecretKey(key)
	         .macAlgorithm(MacAlgorithm.HS512)    // Force HS512 if you sign with HS512
	         .build();
	     return delegate;
	 }
}
