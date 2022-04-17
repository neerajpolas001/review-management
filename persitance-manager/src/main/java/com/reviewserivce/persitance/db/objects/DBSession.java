package com.reviewserivce.persitance.db.objects;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "session")
public class DBSession {

	@Id
	@Column(name = "sessionid")
	private String sessionId;
	
	@Column(name = "userid", nullable = false, unique = true)
	private String userId;
	
	@Column(nullable = false)
	private Date timestamp;

	public DBSession() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DBSession(String sessionId, String userId, Date timestamp) {
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
		DBSession other = (DBSession) obj;
		return Objects.equals(sessionId, other.sessionId) && Objects.equals(timestamp, other.timestamp) && Objects.equals(userId, other.userId);
	}

	@Override
	public String toString() {
		return "DBSession [sessionId=" + sessionId + ", userId=" + userId + ", timestamp=" + timestamp + "]";
	}
	
}
