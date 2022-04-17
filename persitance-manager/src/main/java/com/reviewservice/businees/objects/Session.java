package com.reviewservice.businees.objects;

import java.util.Date;
import java.util.Objects;

public class Session {

	private String sessionId;

	private String userId;

	private Date timestamp;

	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Session(String sessionId, String userId, Date timestamp) {
		super();
		this.sessionId = sessionId;
		this.userId = userId;
		this.timestamp = timestamp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sessionId, timestamp, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		return Objects.equals(sessionId, other.sessionId) && Objects.equals(timestamp, other.timestamp) && Objects.equals(userId, other.userId);
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", userId=" + userId + ", timestamp=" + timestamp + "]";
	}

}
