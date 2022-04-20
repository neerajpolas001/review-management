package com.reviewservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewservice.businees.objects.Session;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.service.SessionPersistenceService;
import com.reviewservice.rest.exceptions.UserServiceException;
import com.reviewservice.utils.SessionUtils;
import com.reviewservice.utils.StringUtils;

@Service
public class SessionService {

	@Autowired
	private SessionPersistenceService sessionPersistenceService;

	public Session validateSession(String sessionId) throws PersistenceServiceException, UserServiceException {
		Session session = sessionPersistenceService.getSessionById(sessionId);
		if (session == null)
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "Invalid Session : session not present");
		if (!SessionUtils.validateSessionTimeout(session))
			throw new UserServiceException(ErrorCode.REQUEST_TIMEOUT, "Session timeout");
		return session;
	}

	public Session createSession(String userId) throws PersistenceServiceException {
		return sessionPersistenceService.createSession(userId);
	}

	public boolean logout(String sessionId) throws UserServiceException, PersistenceServiceException {
		if (StringUtils.isEmpty(sessionId))
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "userId can not be null or empty");
		sessionPersistenceService.logout(sessionId);
		return true;
	}
}
