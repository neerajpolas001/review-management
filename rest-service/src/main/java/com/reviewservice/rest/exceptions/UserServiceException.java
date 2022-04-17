package com.reviewservice.rest.exceptions;

import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.ReviewServiceException;

public class UserServiceException extends ReviewServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserServiceException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserServiceException(ErrorCode errorCode, String message) {
		super(errorCode, message);
		// TODO Auto-generated constructor stub
	}

	public UserServiceException(ErrorCode errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	public UserServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public UserServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UserServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
