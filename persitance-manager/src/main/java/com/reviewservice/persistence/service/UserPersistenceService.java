package com.reviewservice.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewserivce.persitance.db.objects.DBUser;
import com.reviewserivce.persitance.repository.UserRepository;
import com.reviewserivce.persitance.uuid.UID;
import com.reviewservice.businees.objects.User;
import com.reviewservice.persistence.adapters.UserAdapter;

@Service
public class UserPersistenceService {

    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
	List<DBUser> dbUsers = repository.findAll();
	List<User> users = new ArrayList<>();
	for (DBUser dbUser : dbUsers) {
	    users.add(UserAdapter.convertToUser(dbUser));
	}
	// dbUsers.stream().forEach(dbUser ->
	// users.add(UserAdapter.convertToUser(dbUser)));
	// dbUsers.stream().map(dbUser ->
	// UserAdapter.convertToUser(dbUser)).collect(Collectors.toList());
	// return repository.findAll().stream().map(dbUser ->
	// UserAdapter.convertToUser(dbUser)).collect(Collectors.toList());
	return users;
    }

    public User createUser(User user) {
	repository.findByEmail(user.getEmail());
	user.setId(new UID().getUUID());
	DBUser dbUser = repository.save(UserAdapter.convertToDBUser(user));
	if(dbUser != null)
	    return user;
	else
	    return null;
    }

}
