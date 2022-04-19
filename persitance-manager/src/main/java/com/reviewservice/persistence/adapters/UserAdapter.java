package com.reviewservice.persistence.adapters;

import com.reviewserivce.persitance.db.objects.DBUser;
import com.reviewservice.businees.objects.User;

public class UserAdapter {

	public static User convertToUser(DBUser dbUser) {
		// TODO - Password decryption
		return new User(dbUser.getId(), dbUser.getName(), dbUser.getUserName(), dbUser.getEmail(), dbUser.getPassword());
	}

	public static DBUser convertToDBUser(User user) {
		// TODO - Password encryption
		return new DBUser(user.getId(), user.getName(), user.getUserName(), user.getEmail(), user.getPassword());
	}

	public static User convertToUserWithNoPassword(DBUser dbUser) {
		return new User(dbUser.getId(), dbUser.getName(), dbUser.getUserName(), dbUser.getEmail());
	}
}
