package com.reviewservice.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewserivce.persitance.db.objects.DBUser;
import com.reviewserivce.persitance.repository.UserRepository;
import com.reviewserivce.persitance.uuid.UID;
import com.reviewservice.businees.objects.User;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.adapters.UserAdapter;
import com.reviewservice.utils.CollectionUtils;
import com.reviewservice.utils.StringUtils;

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

    public User createUser(User user) throws PersistenceServiceException {
    	if(StringUtils.isEmpty(user.getEmail()))
    	    throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "Email for user can not be empty");
    	List<DBUser> dbUsers = repository.findByEmail(user.getEmail());
    	if(!CollectionUtils.isEmpty(dbUsers))
    	    throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "Email already exists");
    	user.setId(UID.getUUID());
    	DBUser dbUser = repository.save(UserAdapter.convertToDBUser(user));
    	if(dbUser != null)
    	    return user;
    	else
    	    return null;
    }

}
