package com.reviewservice.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reviewservice.businees.objects.Session;
import com.reviewservice.businees.objects.User;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.service.SessionPersistenceService;
import com.reviewservice.persistence.service.UserPersistenceService;
import com.reviewservice.rest.exceptions.UserServiceException;
import com.reviewservice.utils.StringUtils;

@RestController
@RequestMapping("userService")
public class UserManagementController {

	@Autowired
	private UserPersistenceService userPersistenceService;
	
	@Autowired
	private SessionPersistenceService sessionPersistenceService;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userPersistenceService.getAllUsers();
	}
	
	@PostMapping("/users/login")
	public Map<String, String> login(@RequestHeader(name = "userName", required = true) String userName, @RequestHeader(name = "password", required = true) String password) throws UserServiceException, PersistenceServiceException{
		if(StringUtils.isEmpty(userName))
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "userName can not be null or empty");
		if(StringUtils.isEmpty(password))
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "password can not be null or empty");
		User user = userPersistenceService.getUserByUserName(password);
		if(!password.equals(user.getPassword()))
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "Incorrect password");
		Session session = sessionPersistenceService.createSession(user.getId());
		Map<String, String> map = new HashMap<>();
		map.put("message", "Login successful");
		map.put("sessionId", session.getSessionId());
		return map;
	}

	@PostMapping("/createUser")
	public User createUser(@RequestBody @Valid User user) throws PersistenceServiceException, UserServiceException {
		if(!StringUtils.validateUserNameFormat(user.getUserName()))
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "Invalid userName");
		return userPersistenceService.createUser(user);
	}

}
