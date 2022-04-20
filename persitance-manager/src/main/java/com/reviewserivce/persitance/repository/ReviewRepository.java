package com.reviewserivce.persitance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reviewserivce.persitance.db.objects.DBReview;

public interface ReviewRepository extends JpaRepository<DBReview, String> {

}
