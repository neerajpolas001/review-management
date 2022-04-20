package com.reviewservice.persistence.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewserivce.persitance.db.objects.DBSession;
import com.reviewserivce.persitance.db.objects.DBUser;
import com.reviewserivce.persitance.repository.SessionRepository;
import com.reviewserivce.persitance.uuid.UID;
import com.reviewservice.businees.objects.Session;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.adapters.SessionAdapter;
import com.reviewservice.utils.CollectionUtils;
import com.reviewservice.utils.DateUtils;
import com.reviewservice.utils.StringUtils;

@Service
public class SessionPersistenceService {

	@Autowired
	private SessionRepository repository;

	public Session createSession(String userId) throws PersistenceServiceException {
		try {
			List<DBSession> dbSessions = repository.findByUserId(userId);
			DBSession dbSession = null;
			if (!CollectionUtils.isEmpty(dbSessions)) {
				if (dbSessions.size() > 1)
					throw new PersistenceServiceException("Multiple sessions for the same user exists for userId : " + userId);
				dbSession = dbSessions.get(0);
				dbSession.setTimestamp(DateUtils.getDateGMT());
			} else
				dbSession = new DBSession(UID.getUUID(), userId, DateUtils.getDateGMT());
			DBSession dbSessionresult = repository.save(dbSession);
			return SessionAdapter.convertToSession(dbSessionresult);
		} catch (Exception e) {
			throw new PersistenceServiceException(e.getMessage(), e);
		}
	}

	public Session getSessionById(String sessionId) throws PersistenceServiceException {
		try {
			Optional<DBSession> optional = repository.findById(sessionId);
			if (optional.isPresent())
				return SessionAdapter.convertToSession(optional.get());
			else
				return null;
		} catch (Exception e) {
			throw new PersistenceServiceException(e.getMessage(), e);
		}
	}

	public void logout(String sessionId) throws PersistenceServiceException {
		Optional<DBSession> optional = repository.findById(sessionId);
		if (!optional.isPresent())
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "Session does not exist");
		repository.deleteById(sessionId);
	}

}
