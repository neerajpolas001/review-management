package com.reviewservice.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewservice.businees.objects.Subscription;
import com.reviewservice.businees.objects.SubscriptionTypes;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.service.SubscriptionPersistenceService;
import com.reviewservice.rest.exceptions.UserServiceException;
import com.reviewservice.utils.StringUtils;

@Service
public class SubscriptionManagementService {

	@Autowired
	private SubscriptionPersistenceService subscriptionPersistenceService;

	private Map<String, Subscription> nameVsSubscriptionMap;

	public List<Subscription> getAllSubscriptions() throws PersistenceServiceException, UserServiceException {
		return this.subscriptionPersistenceService.getAllSubscriptions();
	}

	public String subscribe(String userId, String subscriptionId) throws UserServiceException {
		try {
			if (StringUtils.isEmptyOrBlank(subscriptionId))
				throw new UserServiceException(ErrorCode.BAD_REQUEST, "Invalid Subscription Id : '" + subscriptionId + "'. subscriptionId can not be null/blank");
			Subscription subscription = subscriptionPersistenceService.getSubscriptionById(subscriptionId);
			if (subscription == null)
				throw new UserServiceException(ErrorCode.BAD_REQUEST, "Invalid Subscription Id : '" + subscriptionId + "'. No such subscription present");
			if (this.subscriptionPersistenceService.isSubscriptionForUserPresent(userId, subscriptionId))
				throw new UserServiceException(ErrorCode.CONFLICT, "User already subscribed to subscriptionId : " + subscriptionId);
			checkSentimentAnalysisSubscriptionCondition(userId, subscription);

			this.subscriptionPersistenceService.subscribe(userId, subscriptionId);
		} catch (PersistenceServiceException e) {
			e.printStackTrace();
		}
		return "Subscription sussessful";
	}

	private void checkSentimentAnalysisSubscriptionCondition(String userId, Subscription targetSubscription) throws PersistenceServiceException, UserServiceException {
		Subscription counterSubscription = null;
		if (SubscriptionTypes.SENTIMENT_ANALYSIS_BASIC.equalTo(targetSubscription.getName()))
			counterSubscription = getsubscriptionByName(SubscriptionTypes.SENTIMENT_ANALYSIS_ADVANCED.name());
		else if (SubscriptionTypes.SENTIMENT_ANALYSIS_ADVANCED.equalTo(targetSubscription.getName()))
			counterSubscription = getsubscriptionByName(SubscriptionTypes.SENTIMENT_ANALYSIS_BASIC.name());
		if (counterSubscription != null && this.subscriptionPersistenceService.isSubscriptionForUserPresent(userId, counterSubscription.getId()))
			throw new UserServiceException(ErrorCode.FORDBIDDEN, "Can not subcribe to " + targetSubscription.getId() + " : " + targetSubscription.getName() + ". "
					+ "User already has subscribed to " + counterSubscription.getName());
	}

	private Subscription getsubscriptionByName(String subscriptionName) throws PersistenceServiceException {
		if (this.nameVsSubscriptionMap == null)
			this.nameVsSubscriptionMap = new HashMap<>();
		Subscription subscription = null;
		if (this.nameVsSubscriptionMap.containsKey(subscriptionName))
			subscription = this.nameVsSubscriptionMap.get(subscriptionName);
		else {
			subscription = subscriptionPersistenceService.getSubscriptionByName(subscriptionName);
			this.nameVsSubscriptionMap.put(subscription.getName(), subscription);
		}
		return subscription;
	}

	public List<Subscription> getAllSubscriptionsForUser(String userId) throws PersistenceServiceException, UserServiceException {
		if (StringUtils.isEmptyOrBlank(userId))
			throw new UserServiceException(ErrorCode.BAD_REQUEST, "Invalid userId Id : '" + userId + "'. userId can not be null/blank");
		return this.subscriptionPersistenceService.getAllSubscriptionsForUser(userId);
	}

}
