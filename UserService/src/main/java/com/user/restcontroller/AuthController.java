package com.user.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.common.view.UserView;
import com.user.model.AuthResponse;
import com.user.model.LoginRequest;
import com.user.model.RefreshTokenRequest;
import com.user.service.AuthService;


@RestController
public class AuthController {

	   @Autowired
	    private AuthService authService;
	    
	    @PostMapping("/api/auth/login")
	    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
	        AuthResponse response = authService.login(request);
	        return ResponseEntity.ok(response);
	    }
	    
	    
	    @PostMapping("/api/auth/refresh")
	    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
	    	System.err.println(request.toString());
	        AuthResponse response = authService.refreshToken(request.getRefreshToken());
	        return ResponseEntity.ok(response);
	    }

//		@RequestMapping(value= "/logout", method = RequestMethod.GET)
//		public void logout(@RequestHeader("Authorization") String accessToken,
//				@AuthenticationPrincipal UserDetails user){
//			System.err.println(accessToken);
//			System.err.println(user.toString());
//			 authService.logout();
//		}
		
}
