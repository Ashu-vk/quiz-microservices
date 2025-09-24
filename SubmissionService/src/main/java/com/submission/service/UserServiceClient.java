package com.submission.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.quiz.common.view.UserView;

@FeignClient(name = "user-service")
@Service
public interface UserServiceClient {


@GetMapping("/users/{id}")
UserView getUserById(@PathVariable("id") Long id);


@PostMapping("/users")
UserView createUser(@RequestBody UserView user);


}
