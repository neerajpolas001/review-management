package com.reviewserivce.persitance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reviewserivce.persitance.db.objects.DBSession;

public interface SessionRepository extends JpaRepository<DBSession, String> {
	
	public List<DBSession> findByUserId(String userId);
	
}
