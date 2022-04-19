package com.reviewserivce.persitance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reviewserivce.persitance.db.objects.DBUserAndSubscription;
import com.reviewserivce.persitance.db.objects.DBUserAndSubscriptionCompositeKey;

public interface UserAndSubscriptionRepository extends JpaRepository<DBUserAndSubscription, DBUserAndSubscriptionCompositeKey> {

	public List<DBUserAndSubscription> findByUserId(String userId);

}
