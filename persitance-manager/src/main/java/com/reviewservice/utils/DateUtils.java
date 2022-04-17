package com.reviewservice.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	public static Date getDateGMT() {
		return Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
	}
}
