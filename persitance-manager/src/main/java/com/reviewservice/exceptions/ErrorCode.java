package com.reviewservice.exceptions;

public enum ErrorCode {

	BAD_REQUEST(400), UNAUTHORISED(401), FORDBIDDEN(403), NOT_FOUND(404), REQUEST_TIMEOUT(408), CONFLICT(409), INTERNAL_SERVER_ERROR(500);

	private final int code;

	ErrorCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
