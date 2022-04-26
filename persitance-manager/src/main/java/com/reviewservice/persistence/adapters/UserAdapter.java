package com.reviewservice.persistence.adapters;

import com.reviewserivce.persitance.db.objects.DBUser;
import com.reviewservice.businees.objects.User;

public class UserAdapter {

	public static User convertToUser(DBUser dbUser) {
		if (dbUser == null)
			return null;
		return new User(dbUser.getId(), dbUser.getName(), dbUser.getUserName(), dbUser.getEmail(), dbUser.getPassword());
	}

	public static DBUser convertToDBUser(User user) {
		if (user == null)
			return null;
		return new DBUser(user.getId(), user.getName(), user.getUserName(), user.getEmail(), user.getPassword());
	}

	public static User convertToUserWithNoPassword(DBUser dbUser) {
		if (dbUser == null)
			return null;
		return new User(dbUser.getId(), dbUser.getName(), dbUser.getUserName(), dbUser.getEmail());
	}
}
