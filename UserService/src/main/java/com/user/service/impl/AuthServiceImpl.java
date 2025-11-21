package com.user.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.model.AuthResponse;
import com.user.model.LoginRequest;
import com.user.model.User;
import com.user.model.UserLoginAudit;
import com.user.repository.UserLoginAuditRepo;
import com.user.repository.UserRepo;
import com.user.service.AuthService;
import com.user.service.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
	 
	private final UserRepo userRepository;
    
    
    private final  JwtService jwtTokenProvider;
    private final UserLoginAuditRepo loginRepo;
    
    @Value("${jwt.expiration}")
    private long jwtExpirationMs;
    
    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder().matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        UserLoginAudit loginAudit = new UserLoginAudit(null, user.getId(), user.getUsername(), null,refreshToken, true);
        loginRepo.save(loginAudit);
        return new AuthResponse(accessToken, refreshToken, "Bearer", jwtExpirationMs / 1000);
    }
    
    @Override
    public AuthResponse refreshToken(String refreshToken) {
    	List<UserLoginAudit> userLoginList = loginRepo.findByRefreshTokenAndRefreshTokenActiveTrue(refreshToken);
    	UserLoginAudit login ;		 
    	if(userLoginList.size()<1) {
    		throw new RuntimeException("User Login Expired. Please login again.");
    	} else if (userLoginList.size()>1) {
    		loginRepo.deleteAll(userLoginList);
    		throw new RuntimeException("User Login Expired. Please login again.");
    	} else {
    		login=userLoginList.get(0);
    	}
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        String newAccessToken = jwtTokenProvider.generateAccessToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
        login.setRefreshToken(newRefreshToken);
        loginRepo.save(login);
        AuthResponse response = new AuthResponse(newAccessToken, newRefreshToken, "Bearer", jwtExpirationMs /  1000);
        return response;
    }

	@Override
	public void logout(Long userId) {
		List<UserLoginAudit> userLoginList = loginRepo.findByUserIdAndRefreshTokenActiveTrue(userId);
		userLoginList.forEach(a->a.setRefreshTokenActive(false));
		loginRepo.saveAll(userLoginList);
	}
	
	  
	    private PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
