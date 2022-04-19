package com.reviewservice.persistence.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewserivce.persitance.db.objects.DBSubscription;
import com.reviewserivce.persitance.db.objects.DBUserAndSubscription;
import com.reviewserivce.persitance.db.objects.DBUserAndSubscriptionCompositeKey;
import com.reviewserivce.persitance.repository.SubscriptionsRepository;
import com.reviewserivce.persitance.repository.UserAndSubscriptionRepository;
import com.reviewservice.businees.objects.Subscription;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.adapters.SubscriptionAdatper;
import com.reviewservice.utils.StringUtils;

@Service
public class SubscriptionPersistenceService {

	@Autowired
	private UserAndSubscriptionRepository userAndSubscriptionRepository;;

	@Autowired
	private SubscriptionsRepository subscriptionsRepository;

	public void subscribe(String userId, String subscriptionId) throws PersistenceServiceException {
		try {
			userAndSubscriptionRepository.save(new DBUserAndSubscription(userId, subscriptionId));
		} catch (Exception e) {
			throw new PersistenceServiceException(e.getMessage(), e);
		}
	}

	public List<Subscription> getAllSubscriptions() throws PersistenceServiceException {
		try {
			return subscriptionsRepository.findAll().stream().map(dbSubscription -> SubscriptionAdatper.convertToSubscription(dbSubscription)).collect(Collectors.toList());
		} catch (Exception e) {
			throw new PersistenceServiceException(e.getMessage(), e);
		}
	}

	public List<Subscription> getAllSubscriptionsForUser(String userId) throws PersistenceServiceException {

		try {
			List<String> subscriptionIds = userAndSubscriptionRepository.findByUserId(userId).stream().map(userAndSub -> userAndSub.getSubscriptionId())
					.collect(Collectors.toList());
			return subscriptionsRepository.findAllById(subscriptionIds).stream().map(dbSub -> SubscriptionAdatper.convertToSubscription(dbSub)).collect(Collectors.toList());
		} catch (Exception e) {
			throw new PersistenceServiceException(e.getMessage(), e);
		}
	}

	public boolean isSubscriptionPresent(String subscriptionId) throws PersistenceServiceException {
		try {
			Optional<DBSubscription> optional = subscriptionsRepository.findById(subscriptionId);
			return optional.isPresent();
		} catch (Exception e) {
			throw new PersistenceServiceException(e.getMessage(), e);
		}
	}

	public boolean isSubscriptionForUserPresent(String userId, String subscriptionId) throws PersistenceServiceException {
		try {
			Optional<DBUserAndSubscription> optional = userAndSubscriptionRepository.findById(new DBUserAndSubscriptionCompositeKey(userId, subscriptionId));
			return optional.isPresent();
		} catch (Exception e) {
			throw new PersistenceServiceException(e.getMessage(), e);
		}
	}
}
