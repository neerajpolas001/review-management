package com.reviewserivce.persitance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reviewserivce.persitance.db.objects.DBSubscription;

public interface SubscriptionsRepository extends JpaRepository<DBSubscription, String> {
	public List<DBSubscription> findByName(String name);
}
