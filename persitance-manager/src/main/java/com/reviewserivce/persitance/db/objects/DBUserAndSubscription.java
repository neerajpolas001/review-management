package com.reviewserivce.persitance.db.objects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "user_and_subs")
@IdClass(DBUserAndSubscriptionCompositeKey.class)
public class DBUserAndSubscription {
	@Id
	@Column(name = "user_id")
	private String userId;

	@Id
	@Column(name = "subscription_id")
	private String subscriptionId;

	public DBUserAndSubscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DBUserAndSubscription(String userId, String subscriptionId) {
		super();
		this.userId = userId;
		this.subscriptionId = subscriptionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(subscriptionId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBUserAndSubscription other = (DBUserAndSubscription) obj;
		return Objects.equals(subscriptionId, other.subscriptionId) && Objects.equals(userId, other.userId);
	}

	@Override
	public String toString() {
		return "DBUseAndSubscription [userId=" + userId + ", subscriptionId=" + subscriptionId + "]";
	}

}