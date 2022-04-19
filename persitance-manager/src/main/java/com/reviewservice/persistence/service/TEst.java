package com.reviewservice.persistence.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.reviewservice.businees.objects.Session;
import com.reviewservice.utils.DateUtils;
import com.reviewservice.utils.SessionUtils;

public class TEst {

	public static void main(String[] args) {
		/*
		 * SimpleDateFormat sdf = new SimpleDateFormat();
		 * sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		 * 
		 * Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT")); Date
		 * date = calendar.getTime();
		 */
		
		Date date = DateUtils.getDateGMT();
		date = new Date(date.getTime() - 108000000);
		System.out.println(SessionUtils.validateSessionTimeout(new Session(null, null, date)));
		
	}
}
