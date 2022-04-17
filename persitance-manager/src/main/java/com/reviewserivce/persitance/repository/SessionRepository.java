package com.reviewserivce.persitance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reviewserivce.persitance.db.objects.DBSession;

public interface SessionRepository extends JpaRepository<DBSession, String> {

}
