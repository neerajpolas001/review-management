package com.reviewservice.exception.advice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.rest.exceptions.UserServiceException;

@RestControllerAdvice
public class ApplicationExceptionAdvice {

	Logger logger = LoggerFactory.getLogger(ApplicationExceptionAdvice.class);

	@ExceptionHandler(PersistenceServiceException.class)
	public ResponseEntity<Map<String, String>> handlePersistenceServiceException(PersistenceServiceException e) {
		logger.error(e.getMessage(), e);
		Map<String, String> map = new HashMap<>();
		if (e.getErrorCode() == ErrorCode.INTERNAL_SERVER_ERROR)
			map.put("Message", "Internal state error");
		else
			map.put("Message", e.getMessage());
		map.put("ErrorCode", e.getErrorCode().toString());
		return ResponseEntity.status(e.getErrorCode().getCode()).body(map);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		logger.error(e.getMessage(), e);
		Map<String, String> map = new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach(error -> map.put(error.getField(), error.getDefaultMessage()));
		map.put("errorCode", ErrorCode.BAD_REQUEST.getCode() + "");
		return ResponseEntity.status(ErrorCode.BAD_REQUEST.getCode()).body(map);
	}

	@ExceptionHandler(UserServiceException.class)
	public ResponseEntity<Map<String, String>> handleUserServiceException(UserServiceException e) {
		logger.error(e.getMessage(), e);
		Map<String, String> map = new HashMap<>();
		if (e.getErrorCode() == ErrorCode.INTERNAL_SERVER_ERROR)
			map.put("Message", "Internal error");
		else
			map.put("Message", e.getMessage());
		map.put("ErrorCode", e.getErrorCode().toString());
		return ResponseEntity.status(e.getErrorCode().getCode()).body(map);
	}

}
