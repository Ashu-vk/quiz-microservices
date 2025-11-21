package com.submission.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${jwt.secret}")
	private String jwtSecret;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(auth->auth.anyRequest().authenticated())
		.oauth2ResourceServer(oauth->oauth.jwt(jwt-> jwt.decoder(jwtDecoder())));
		return http.build();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
	    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	    NimbusJwtDecoder decoder = NimbusJwtDecoder
	            .withSecretKey(key)
	            .macAlgorithm(MacAlgorithm.HS512)
	            .build();

	    return token -> {
	        return decoder.decode(token);
	    };
	}
}
