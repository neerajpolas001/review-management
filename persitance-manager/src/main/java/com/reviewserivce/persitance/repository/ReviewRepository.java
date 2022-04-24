package com.reviewserivce.persitance.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reviewserivce.persitance.db.objects.DBReview;

public interface ReviewRepository extends JpaRepository<DBReview, String> {

	@Query("FROM #{#entityName} WHERE user_id =?1")
	public List<DBReview> findByUserId(String userId, Pageable pageable);

	@Query("FROM #{#entityName} WHERE user_id = ?1 AND branch_id =?2")
	public List<DBReview> findByBrachId(String userId, String branchId, Pageable pageable);

	@Query("FROM #{#entityName} WHERE user_id = ?1 AND sentiment =?2")
	public List<DBReview> findBySentiment(String userId, String sentiment, Pageable pageable);

	@Query("FROM #{#entityName} WHERE user_id = ?1 AND branch_id = ?2 AND sentiment = ?3")
	public List<DBReview> findByBrachIdAndSentiment(String userId, String branchId, String sentiment, Pageable pageable);
}
