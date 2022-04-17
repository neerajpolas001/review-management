package com.reviewservice.persistence.adapters;

import com.reviewserivce.persitance.db.objects.DBSession;
import com.reviewservice.businees.objects.Session;

public class SessionAdapter {

	public static Session convertToSession(DBSession dbSession) {
		return new Session(dbSession.getSessionId(), dbSession.getUserId(), dbSession.getTimestamp());
	}

	public static DBSession convertToDBSession(Session session) {
		return new DBSession(session.getSessionId(), session.getUserId(), session.getTimestamp());
	}

}
