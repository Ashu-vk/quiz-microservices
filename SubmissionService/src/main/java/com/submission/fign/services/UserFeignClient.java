package com.submission.fign.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.quiz.common.view.UserView;
import com.submission.fign.config.FeignConfig;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserFeignClient {


@GetMapping("/users/{id}")
UserView getUserById(@PathVariable("id") Long id);

}
