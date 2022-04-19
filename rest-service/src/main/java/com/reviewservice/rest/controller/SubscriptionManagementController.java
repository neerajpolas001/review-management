package com.reviewservice.rest.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reviewservice.businees.objects.Subscription;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.service.SubscriptionPersistenceService;
import com.reviewservice.rest.exceptions.UserServiceException;
import com.reviewservice.services.SessionService;
import com.reviewservice.utils.StringUtils;

@RestController
@RequestMapping("subcription-management")
public class SubscriptionManagementController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private SubscriptionPersistenceService subscriptionPersistenceService;

	@GetMapping("/subscriptions")
	public List<Subscription> getAllSubcriptions(@RequestHeader(name = "sessionId", required = true) String sessionId) throws PersistenceServiceException, UserServiceException {
		sessionService.validateSession(sessionId).getUserId();
		return subscriptionPersistenceService.getAllSubscriptions();
	}

	@PostMapping("/subscriptions/{subscriptionId}")
	public String subscribe(@RequestHeader(name = "sessionId", required = true) String sessionId, @PathVariable(required = true) String subscriptionId)
			throws PersistenceServiceException, UserServiceException {
		if(StringUtils.isEmptyOrBlank(subscriptionId))
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "Invalid Subscription Id : '" + subscriptionId + "'. subscriptionId can not be null/blank");
		String userId = sessionService.validateSession(sessionId).getUserId();
		if (!subscriptionPersistenceService.isSubscriptionPresent(subscriptionId))
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "Invalid Subscription Id : '" + subscriptionId + "'. No such subscription present");
		if(subscriptionPersistenceService.isSubscriptionForUserPresent(userId, subscriptionId))
			throw new UserServiceException(ErrorCode.CONFLICT, "User already subscribed to subscriptionId : " + subscriptionId);
		subscriptionPersistenceService.subscribe(userId, subscriptionId);
		return "Subscription sussessful";
	}

}
