package com.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.user.service.AuthService;

import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Autowired
    private AuthService authService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**").permitAll()
                    .anyRequest().authenticated()
                ).logout(logout -> logout.logoutUrl("/logout")
                		.addLogoutHandler(logoutHandler()))
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(jwtDecoder()))
            
            );
        
        return http.build();
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(
            Keys.hmacShaKeyFor(jwtSecret.getBytes())
        ).macAlgorithm(MacAlgorithm.HS512)
        		.build();
    }
    
 
    
    @Bean 
    public LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {

            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                System.out.println("No Authorization header");
                return;
            }

            String token = header.substring(7);
            Jwt decoded = jwtDecoder().decode(token);

            System.out.println("Logout - username: " + decoded.getSubject());
            System.out.println("Logout - userId: " + decoded.getClaim("userId"));
            authService.logout(Long.valueOf(decoded.getClaim("userId")));
            

           
    };
    }

}