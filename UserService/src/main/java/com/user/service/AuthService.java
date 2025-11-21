package com.user.service;

import com.user.model.AuthResponse;
import com.user.model.LoginRequest;

public interface AuthService {

	AuthResponse login(LoginRequest request);

	AuthResponse refreshToken(String refreshToken);

	void logout(Long valueOf);

}
