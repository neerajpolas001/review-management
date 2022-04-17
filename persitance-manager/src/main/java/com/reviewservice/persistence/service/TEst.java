package com.reviewservice.persistence.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TEst {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Date date = calendar.getTime();
	}
}
