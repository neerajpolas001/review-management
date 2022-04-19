package com.reviewservice.utils;

public class StringUtils {

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}
	
	public static boolean isEmptyOrBlank(String str) {
		 if(StringUtils.isEmpty(str))
			 return false;
		 return StringUtils.isEmpty(str.trim());
	}


	public static boolean validateUserNameFormat(String userName) {
		if (userName == null || userName.isEmpty())
			return false;
		if (userName.contains(" "))
			return false;
		//boolean x = userName.matches("[^\\/,\"']\\S*");
		boolean x = userName.contains(" ") || userName.contains("\\") || 
				userName.contains("/") || userName.contains(",") || userName.contains("\"")
				|| 		userName.contains("'");
		return !x;

	}
}
