package com.reviewservice.exception.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reviewservice.exceptions.PersistenceServiceException;

@RestControllerAdvice
public class ApplicationExceptionAdvice {

	@ExceptionHandler(PersistenceServiceException.class)
	public ResponseEntity<Map<String, String>> handlePersistenceServiceException(PersistenceServiceException e){
		Map<String, String> map = new HashMap<>();
		map.put("Message", e.getMessage());
		map.put("ErrorCode", e.getErrorCode().toString());
		return ResponseEntity.status(e.getErrorCode().getCode()).body(map);
	}
}
