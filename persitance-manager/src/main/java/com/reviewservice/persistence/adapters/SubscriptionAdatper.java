package com.reviewservice.persistence.adapters;

import java.util.List;
import java.util.stream.Collectors;

import com.reviewserivce.persitance.db.objects.DBSubscription;
import com.reviewservice.businees.objects.Subscription;

public class SubscriptionAdatper {

	public static Subscription convertToSubscription(DBSubscription dbSubscription) {
		if (dbSubscription == null)
			return null;
		return new Subscription(dbSubscription.getId(), dbSubscription.getName(), dbSubscription.getDescription());
	}

	public static DBSubscription convertToDBSubscription(Subscription subscription) {
		if (subscription == null)
			return null;
		return new DBSubscription(subscription.getId(), subscription.getName(), subscription.getDescription());
	}

	public static List<Subscription> convertToSubscription(List<DBSubscription> dbSubscriptions) {
		if (dbSubscriptions == null)
			return null;
		return dbSubscriptions.stream().map(sub -> SubscriptionAdatper.convertToSubscription(sub)).collect(Collectors.toList());
	}

	public static List<DBSubscription> convertToDBSubscription(List<Subscription> subscriptions) {
		if (subscriptions == null)
			return null;
		return subscriptions.stream().map(dbSub -> SubscriptionAdatper.convertToDBSubscription(dbSub)).collect(Collectors.toList());
	}
}
