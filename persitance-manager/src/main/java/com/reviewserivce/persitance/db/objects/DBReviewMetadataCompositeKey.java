package com.reviewserivce.persitance.db.objects;

import java.io.Serializable;
import java.util.Objects;

public class DBReviewMetadataCompositeKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reviewId;

	private String key;

	public DBReviewMetadataCompositeKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DBReviewMetadataCompositeKey(String reviewId, String key) {
		super();
		this.reviewId = reviewId;
		this.key = key;
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, reviewId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBReviewMetadataCompositeKey other = (DBReviewMetadataCompositeKey) obj;
		return Objects.equals(key, other.key) && Objects.equals(reviewId, other.reviewId);
	}

	@Override
	public String toString() {
		return "DBReviewMetadataCompositeKey [reviewId=" + reviewId + ", key=" + key + "]";
	}

}
