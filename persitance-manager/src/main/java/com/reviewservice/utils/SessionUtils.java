package com.reviewservice.utils;

import com.reviewservice.businees.objects.Session;

public class SessionUtils {

	/**
	 * 2 hours = 7200000 1 hour = 3600000
	 */
	private static final long SESSION_TIME = 7200000;

	public static final boolean validateSessionTimeout(Session session) {
		if(session == null)
			return false;
		if(session.getTimestamp() == null)
			return false;
		return DateUtils.getTimeDifference(DateUtils.getDateGMT(), session.getTimestamp()) < SESSION_TIME;
	}

}
