package com.reviewserivce.persitance.db.objects;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class DBUserAndSubscriptionCompositeKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private String subscriptionId;

	public DBUserAndSubscriptionCompositeKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DBUserAndSubscriptionCompositeKey(String userId, String subscriptionId) {
		super();
		this.userId = userId;
		this.subscriptionId = subscriptionId;
	}

	public DBUserAndSubscriptionCompositeKey(String userId) {
		super();
		this.userId = userId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		DBUserAndSubscriptionCompositeKey other = (DBUserAndSubscriptionCompositeKey) obj;
		return Objects.equals(subscriptionId, other.subscriptionId) && Objects.equals(userId, other.userId);
	}

	@Override
	public String toString() {
		return "DBSubscriptionCompositeKey [userId=" + userId + ", subscriptionId=" + subscriptionId + "]";
	}
}