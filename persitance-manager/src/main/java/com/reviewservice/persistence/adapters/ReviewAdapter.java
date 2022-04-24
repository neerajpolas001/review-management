package com.reviewservice.persistence.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.reviewserivce.persitance.db.objects.DBReview;
import com.reviewserivce.persitance.db.objects.DBReview.DBReviewBuilder;
import com.reviewserivce.persitance.db.objects.DBReviewMetadata;
import com.reviewservice.businees.objects.Review;
import com.reviewservice.businees.objects.Review.ReviewBuilder;
import com.reviewservice.utils.CollectionUtils;

public class ReviewAdapter {

	public static Review convertToReview(DBReview dbReview, List<DBReviewMetadata> dbReviewMetadataList) {
		ReviewBuilder reviewBuilder = new Review.ReviewBuilder()
				.id(dbReview.getId())
				.userId(dbReview.getUserId())
				.text(dbReview.getText())
				.branchId(dbReview.getBranchId())
				.branchName(dbReview.getBranchName())
				.orderType(dbReview.getOrderType())
				.dateCreated(dbReview.getDateCreated())
				.dateModiefied(dbReview.getDateModiefied())
				.rating(dbReview.getRating())
				.votes(dbReview.getVotes())
				.reviewerName(dbReview.getReviewerName())
				.reviewerEmail(dbReview.getReviewerEmail())
				.city(dbReview.getCity())
				.state(dbReview.getState())
				.country(dbReview.getCountry())
				.sentiment(dbReview.getSentiment())
				.polarity(dbReview.getPolarity());
		if (!CollectionUtils.isEmpty(dbReviewMetadataList)) {
			for (DBReviewMetadata dbReviewMetadata : dbReviewMetadataList) {
				HashMap<String, String> map = new HashMap<>();
				map.put(dbReviewMetadata.getKey(), dbReviewMetadata.getValue());
			}
		}
		return reviewBuilder.build();
	}

	public static DBReview convertToDBReview(Review review) {
		DBReviewBuilder reviewBuilder = new DBReview.DBReviewBuilder()
				.id(review.getId())
				.userId(review.getUserId())
				.text(review.getText())
				.branchId(review.getBranchId())
				.branchName(review.getBranchName())
				.orderType(review.getOrderType())
				.dateCreated(review.getDateCreated())
				.dateModiefied(review.getDateModiefied())
				.rating(review.getRating())
				.votes(review.getVotes())
				.reviewerName(review.getReviewerName())
				.reviewerEmail(review.getReviewerEmail())
				.city(review.getCity())
				.state(review.getState())
				.country(review.getCountry())
				.sentiment(review.getSentiment())
				.polarity(review.getPolarity());
		List<DBReviewMetadata> dbReviewMetadataList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(review.getMetaData())) {
			for (Entry<String, String> entry : review.getMetaData().entrySet()) {
				dbReviewMetadataList.add(new DBReviewMetadata(null, entry.getKey(), entry.getValue()));
			}
		}
		return reviewBuilder.build();
	}

}
