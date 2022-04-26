package com.reviewserivce.persitance.db.objects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class DBUser {

	@Id
	private String id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(name = "username", nullable = false, unique = true, length = 100)
	private String userName;

	@Column(nullable = false, length = 100)
	private String email;

	@Column(nullable = false, length = 255)
	private String password;

	public DBUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DBUser(String id, String name, String userName, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public DBUser(Builder builder) {
		super();
		this.id = builder.id;
		this.name = builder.name;
		this.userName = builder.userName;
		this.email = builder.email;
		this.password = builder.password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, name, password, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBUser other = (DBUser) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(userName, other.userName);
	}

	@Override
	public String toString() {
		return "DBUser [id=" + id + ", name=" + name + ", userName=" + userName + ", email=" + email + ", password=" + password + "]";
	}

	public static class Builder {
		private String id;

		private String name;

		private String userName;

		private String email;

		private String password;

		public Builder() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public DBUser build() {
			return new DBUser(this);
		}

	}

}
