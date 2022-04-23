package com.reviewservice.rest.controller;

import java.util.List;

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
import com.reviewservice.rest.exceptions.UserServiceException;
import com.reviewservice.services.SessionService;
import com.reviewservice.services.SubscriptionManagementService;
import com.reviewservice.utils.StringUtils;

@RestController
@RequestMapping("subcription-management")
public class SubscriptionManagementController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private SubscriptionManagementService subscriptionManagementService;

	@GetMapping("/subscriptions")
	public List<Subscription> getAllSubscriptions(@RequestHeader(name = "sessionId", required = true) String sessionId) throws PersistenceServiceException, UserServiceException {
		sessionService.validateSession(sessionId);
		return subscriptionManagementService.getAllSubscriptions();
	}

	@PostMapping("/subscriptions/{subscriptionId}")
	public String subscribe(@RequestHeader(name = "sessionId", required = true) String sessionId, @PathVariable(required = true) String subscriptionId)
			throws PersistenceServiceException, UserServiceException {
		String userId = sessionService.validateSession(sessionId).getUserId();
		subscriptionManagementService.subscribe(userId, subscriptionId);
		return "Subscription sussessful";
	}

	@GetMapping("/{userId}/subscriptions")
	public List<Subscription> getAllSubscriptionsForUser(@RequestHeader(name = "sessionId", required = true) String sessionId, @PathVariable(required = true) String userId)
			throws PersistenceServiceException, UserServiceException {
		String userIdFromSession = sessionService.validateSession(sessionId).getUserId();
		if (StringUtils.isEmptyOrBlank(userId))
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "Invalid userId Id : '" + userId + "'. userId can not be null/blank");
		if (!userIdFromSession.equals(userId))
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "Session does not match the userId, provided");
		return subscriptionManagementService.getAllSubscriptionsForUser(userId);
	}

}
