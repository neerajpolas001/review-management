package com.reviewserivce.persitance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reviewserivce.persitance.db.objects.DBReviewMetadata;
import com.reviewserivce.persitance.db.objects.DBReviewMetadataCompositeKey;

public interface ReviewMetaDataRepository extends JpaRepository<DBReviewMetadata, DBReviewMetadataCompositeKey> {
	public List<DBReviewMetadata> findByReviewId(String reviewId);
}
