package com.user.service;

import com.user.model.User;

public interface JwtService {

	String generateAccessToken(User user);

	String generateRefreshToken(User user);

	String getUsernameFromToken(String token);

	boolean validateToken(String token);

}
