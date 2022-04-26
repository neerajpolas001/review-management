package com.reviewservice.persistence.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
		 * users.add(UserAdapter.convertToUserWithNoPassword(dbUser)); } return users;
		 */
		return repository.findAll().stream().map(dbUser -> UserAdapter.convertToUserWithNoPassword(dbUser)).collect(Collectors.toList());

	}

	public User createUser(User user) throws PersistenceServiceException {
		if (checkIfExists(new DBUser.Builder().userName(user.getUserName()).build()))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userName already exists");
		user.setId(UID.getUUID());
		DBUser dbUser = repository.save(UserAdapter.convertToDBUser(user));
		return UserAdapter.convertToUser(dbUser);

	}

	private boolean checkIfExists(DBUser dbUser) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
		Example<DBUser> example = Example.of(dbUser, matcher);
		return this.repository.exists(example);
	}

	public User getUserByUserName(String userName) throws PersistenceServiceException {
		if (StringUtils.isEmptyOrBlank(userName))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userName can not be null/empty/blank");
		List<DBUser> dbUsers = repository.findByUserName(userName);
		if (CollectionUtils.isEmpty(dbUsers))
			throw new PersistenceServiceException(ErrorCode.NOT_FOUND, "usename not found");
		return UserAdapter.convertToUser(dbUsers.get(0));
	}

	public User getUserById(String userId) throws PersistenceServiceException {
		if (StringUtils.isEmptyOrBlank(userId))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userId can not be null/empty/blank");
		Optional<DBUser> optional = this.repository.findById(userId);
		if (!optional.isPresent())
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userId does not exist");
		return UserAdapter.convertToUserWithNoPassword(optional.get());
	}
}
