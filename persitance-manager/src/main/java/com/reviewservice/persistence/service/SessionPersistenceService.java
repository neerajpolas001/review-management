package com.reviewservice.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewserivce.persitance.db.objects.DBSession;
import com.reviewserivce.persitance.repository.SessionRepository;
import com.reviewserivce.persitance.uuid.UID;
import com.reviewservice.businees.objects.Session;
import com.reviewservice.persistence.adapters.SessionAdapter;
import com.reviewservice.utils.DateUtils;

@Service
public class SessionPersistenceService {

	@Autowired
	private SessionRepository repository;
	
	
	public Session createSession(String userId) {
		DBSession dbSession = repository.save(new DBSession(UID.getUUID().toString(), userId, DateUtils.getDateGMT()));
		return  SessionAdapter.convertToSession(dbSession);
	}
	
	
}
