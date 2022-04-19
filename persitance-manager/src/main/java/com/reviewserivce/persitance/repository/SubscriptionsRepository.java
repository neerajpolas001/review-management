package com.reviewserivce.persitance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reviewserivce.persitance.db.objects.DBSubscription;

public interface SubscriptionsRepository extends JpaRepository<DBSubscription, String> {

}
