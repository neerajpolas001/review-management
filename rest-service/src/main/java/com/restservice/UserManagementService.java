package com.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reviewservice.businees.objects.User;
import com.reviewservice.persistence.service.UserPersistenceService;

@RestController
@RequestMapping("userService")
public class UserManagementService {

    @Autowired
    private UserPersistenceService userPersistenceService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
	return userPersistenceService.getAllUsers();
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
	return userPersistenceService.createUser(user);
    }
    

}
