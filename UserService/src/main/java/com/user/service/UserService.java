package com.user.service;

import java.util.List;
import java.util.Optional;

import com.quiz.common.view.UserView;

public interface UserService {

	Optional<UserView> getUserById(Long userId);

	List<UserView> getAllUsers();

	Optional<UserView> saveOrUpdate(UserView view);

}
