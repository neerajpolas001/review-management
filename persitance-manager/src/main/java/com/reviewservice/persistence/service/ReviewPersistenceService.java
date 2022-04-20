package com.reviewservice.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.reviewserivce.persitance.db.objects.DBReview;
import com.reviewserivce.persitance.db.objects.DBReviewMetadata;
import com.reviewserivce.persitance.repository.ReviewMetaDataRepository;
import com.reviewserivce.persitance.repository.ReviewRepository;
import com.reviewserivce.persitance.uuid.UID;
import com.reviewservice.businees.objects.Review;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.adapters.ReviewAdapter;
import com.reviewservice.utils.CollectionUtils;
import com.reviewservice.utils.DateUtils;
import com.reviewservice.utils.StringUtils;

@Service
public class ReviewPersistenceService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ReviewMetaDataRepository reviewMetaDataRepository;

	@Transactional // (readOnly = false, isolation = Isolation.READ_COMMITTED, propagation
					// =Propagation.REQUIRED )
	public Review saveReview(Review review) throws PersistenceServiceException {
		if (StringUtils.isEmpty(review.getUserId()))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userId in review can not be null/empty");
		if (StringUtils.isEmpty(review.getText()))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "text of the review can not null/empty");
		DBReview dbReview = ReviewAdapter.convertToDBReview(review);
		dbReview.setId(UID.getUUID());
		Date date = DateUtils.getDateGMT();
		dbReview.setDateCreated(date);
		dbReview.setDateModiefied(date);
		DBReview dbReviewResponse = reviewRepository.save(dbReview);
		List<DBReviewMetadata> dbReviewMetadatasList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(review.getMetaData())) {
			for (Entry<String, String> entry : review.getMetaData().entrySet()) {
				dbReviewMetadatasList.add(new DBReviewMetadata(dbReview.getId(), entry.getKey(), entry.getValue()));
			}
		}
		List<DBReviewMetadata> dbReviewMetadataResponseList = reviewMetaDataRepository.saveAll(dbReviewMetadatasList);
		return ReviewAdapter.convertToReview(dbReviewResponse, dbReviewMetadataResponseList);
	}

}
