package com.reviewservice.utils;

import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;

public class Test {

	
	public static void main(String[] args) {
		
		PersistenceServiceException x = new PersistenceServiceException(ErrorCode.BAD_REQUEST, "dsdsd"); 
	}
}
