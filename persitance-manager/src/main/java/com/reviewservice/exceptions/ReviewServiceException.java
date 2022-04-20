package com.reviewservice.exceptions;

public abstract class ReviewServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

	public ReviewServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReviewServiceException(ErrorCode errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;

	}

	public ReviewServiceException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		// TODO Auto-generated constructor stub
	}

	public ReviewServiceException(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public ReviewServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ReviewServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ReviewServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
