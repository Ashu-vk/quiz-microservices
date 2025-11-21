package com.user.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.model.User;
import com.user.repository.UserRepo;
import com.user.service.UserService;
import com.quiz.common.view.UserView;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo repository;
	
	@Override
	public Optional<UserView> getUserById(Long userId) {
		if (userId == null) {
			return Optional.empty();
		}
		return toView(repository.findById(userId).orElseThrow(()->new RuntimeException("User not found with id: " +userId)));
	}
	
	@Override
	public List<UserView> getAllUsers() {
		return toViewList(repository.findAll());
	}
	@Override
	public Optional<UserView> saveOrUpdate(UserView view) {
		if(view == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		User user = toEntity(view);
		if(user.getId()==null) {
			user.setCreatedAt(LocalDateTime.now());
			user.setPassword("a.123456");
			
		} 
			user.setUpdatedAt(LocalDateTime.now());
		
		return toView(repository.save(user));
	}
	
	private List<UserView> toViewList(List<User> users) {
		return Objects.requireNonNullElse(users, new ArrayList<User>())
				.stream().map(this::toView).map(Optional::get).toList();
	}
	
	private User toEntity(UserView view) {
	        if (view == null) return null;

	        return User.builder()
	                .id(view.getId())
	                .username(view.getName())
	                .email(view.getEmail())
	                .role(view.getRole())
	                .createdAt(view.getCreatedAt())
	                .updatedAt(view.getUpdatedAt())
	                .build();
	    }
	private Optional<UserView> toView(User user) {
		return Optional.of(user).map(u->
			 UserView.builder()
		     .id(u.getId())
		     .name(u.getUsername())
		     .email(u.getEmail())
		     .role(u.getRole())
		     .createdAt(u.getCreatedAt())
		     .updatedAt(u.getUpdatedAt())
		     .build()
			);
	}

}
