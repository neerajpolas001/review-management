package com.reviewservice.persistence.adapters;

import com.reviewserivce.persitance.db.objects.DBSubscription;
import com.reviewservice.businees.objects.Subscription;

public class SubscriptionAdatper {

	public static Subscription convertToSubscription(DBSubscription dbSubscription) {

		return new Subscription(dbSubscription.getId(), dbSubscription.getName(), dbSubscription.getDescription());
	}

	public static DBSubscription convertToDBSubscription(Subscription subscription) {

		return new DBSubscription(subscription.getId(), subscription.getName(), subscription.getDescription());
	}
}
