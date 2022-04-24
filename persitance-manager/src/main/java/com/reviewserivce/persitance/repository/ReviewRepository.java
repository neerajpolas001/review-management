package com.reviewserivce.persitance.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.reviewserivce.persitance.db.objects.DBReview;

public interface ReviewRepository extends JpaRepository<DBReview, String> {

	@Query("FROM #{#entityName} WHERE user_id =?1")
	public List<DBReview> findByUserId(String userId, Pageable pageable);
}
