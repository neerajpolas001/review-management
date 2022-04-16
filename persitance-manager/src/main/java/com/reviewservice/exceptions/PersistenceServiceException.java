package com.reviewservice.exceptions;

public class PersistenceServiceException extends ReviewServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersistenceServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersistenceServiceException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
		// TODO Auto-generated constructor stub
	}

	public PersistenceServiceException(ErrorCode errorCode, String message) {
		super(errorCode, message);
		// TODO Auto-generated constructor stub
	}

	public PersistenceServiceException(ErrorCode errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	public PersistenceServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public PersistenceServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PersistenceServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PersistenceServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
