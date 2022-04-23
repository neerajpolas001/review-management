package com.reviewservice.async.exceptions;

import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.ReviewServiceException;

public class ReviewServiceAsyncException extends ReviewServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReviewServiceAsyncException() {
		super();
	}

	public ReviewServiceAsyncException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public ReviewServiceAsyncException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	public ReviewServiceAsyncException(ErrorCode errorCode) {
		super(errorCode);
	}

	public ReviewServiceAsyncException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReviewServiceAsyncException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReviewServiceAsyncException(String message) {
		super(message);
	}

	public ReviewServiceAsyncException(Throwable cause) {
		super(cause);
	}

}
