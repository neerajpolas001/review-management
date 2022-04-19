package com.reviewservice.persistence.service;

import java.util.List;
import java.util.stream.Collectors;

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
		/*
		 * List<DBUser> dbUsers = repository.findAll(); List<User> users = new
		 * ArrayList<>(); for (DBUser dbUser : dbUsers) {
		 * users.add(UserAdapter.convertToUserWithNoPassword(dbUser)); }
		 * return users;
		 */
		return repository.findAll().stream().map(dbUser -> UserAdapter.convertToUserWithNoPassword(dbUser)).collect(Collectors.toList());
		
	}

	public User createUser(User user) throws PersistenceServiceException {
		if (StringUtils.isEmpty(user.getEmail()))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "Email for user can not be empty");
		if (StringUtils.isEmpty(user.getUserName()))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userName for user can not be empty");
		List<DBUser> dbUsers = repository.findByUserName(user.getUserName());
		if (!CollectionUtils.isEmpty(dbUsers))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userName already exists");
		if (dbUsers.size() > 1)
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "Invalid Database state, Multiple ids with 'userName' exists");
		user.setId(UID.getUUID());
		try {
			DBUser dbUser = repository.save(UserAdapter.convertToDBUser(user));
			if (dbUser != null)
				return user;
			else
				return null;
		} catch (Exception e) {
			throw new PersistenceServiceException(e.getMessage(), e);
		}
	}

	public String login(String userName, String password) throws PersistenceServiceException {

		if (StringUtils.isEmpty(userName))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userName can not be null or empty");
		if (StringUtils.isEmpty(password))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "password can not be null or empty");

		List<DBUser> dbUsers = repository.findByUserName(userName);
		if (CollectionUtils.isEmpty(dbUsers))
			throw new PersistenceServiceException(ErrorCode.NOT_FOUND, "usename not found");
		if (dbUsers.size() > 1)
			throw new PersistenceServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "Multiple Username for found with userName: " + userName);

		return password;

	}

	public User getUserByUserName(String userName) throws PersistenceServiceException {
		if (StringUtils.isEmpty(userName))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userName can not be null or empty");
		List<DBUser> dbUsers = repository.findByUserName(userName);
		if (CollectionUtils.isEmpty(dbUsers))
			throw new PersistenceServiceException(ErrorCode.NOT_FOUND, "usename not found");
		if (dbUsers.size() > 1)
			throw new PersistenceServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "Multiple Username for found with userName: " + userName);
		return UserAdapter.convertToUser(dbUsers.get(0));
	}
}
