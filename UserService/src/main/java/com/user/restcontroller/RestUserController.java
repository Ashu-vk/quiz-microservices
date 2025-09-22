package com.user.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.UserService;
import com.user.view.UserView;

@RestController
public class RestUserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	public List<UserView> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public UserView saveUsers(@RequestBody UserView userView){
		return userService.saveOrUpdate(userView).orElseThrow();
	}
	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET, produces = "application/json")
	public UserView getUsers(@PathVariable(value = "userId") Long userId){
		return userService.getUserById(userId).orElseThrow();
	}
}
