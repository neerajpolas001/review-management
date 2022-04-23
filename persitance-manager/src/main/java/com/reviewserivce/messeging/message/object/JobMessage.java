package com.reviewserivce.messeging.message.object;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class JobMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String subscriptionType;

	private HashMap<String, String> body;

	public JobMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobMessage(String subscriptionType, HashMap<String, String> body) {
		super();
		this.subscriptionType = subscriptionType;
		this.body = body;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public HashMap<String, String> getBody() {
		return body;
	}

	public void setBody(HashMap<String, String> body) {
		this.body = body;
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, subscriptionType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobMessage other = (JobMessage) obj;
		return Objects.equals(body, other.body) && Objects.equals(subscriptionType, other.subscriptionType);
	}

	@Override
	public String toString() {
		return "JobMessage [subscriptionType=" + subscriptionType + ", body=" + body + "]";
	}

}
