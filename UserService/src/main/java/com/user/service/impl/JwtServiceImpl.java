package com.user.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.user.model.User;
import com.user.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
	  @Value("${jwt.secret}")
	    private String jwtSecret;
	    
	    @Value("${jwt.expiration}")
	    private long jwtExpirationMs;
	    
	    @Value("${jwt.refresh-expiration}")
	    private long jwtRefreshExpirationMs;
	    
	    @Override
	    public String generateAccessToken(User user) {
	        Date now = new Date();
	        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
	        
	        return Jwts.builder()
	                .subject(user.getUsername())
	                .claim("userId", user.getId())
	                .claim("email", user.getEmail())
	                .claim("roles", user.getRole())
	                .claim("type", "access")
	                .issuedAt(now)
	                .expiration(expiryDate)
	                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), Jwts.SIG.HS512)
	                .compact();
	    }
	    
	    @Override
	    public String generateRefreshToken(User user) {
	        Date now = new Date();
	        Date expiryDate = new Date(now.getTime() + jwtRefreshExpirationMs);
	        
	        return Jwts.builder()
	                .subject(user.getUsername())
	                .claim("userId", user.getId())
	                .claim("type", "refresh")
	                .issuedAt(now)
	                .expiration(expiryDate)
	                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), Jwts.SIG.HS512)
	                .compact();
	    }
	    
	    @Override
	    public String getUsernameFromToken(String token) {
	        Claims claims = Jwts.parser()
	                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	        
	        return claims.getSubject();
	    }
	    
	    @Override
	    public boolean validateToken(String token) {
	        try {
	            Jwts.parser()
	                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
	                .build()
	                .parseSignedClaims(token);
	            return true;
	        } catch (JwtException | IllegalArgumentException e) {
	            return false;
	        }
	    }
}
