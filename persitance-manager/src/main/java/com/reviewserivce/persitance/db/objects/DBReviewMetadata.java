package com.reviewserivce.persitance.db.objects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "review_metadata")
@IdClass(DBReviewMetadataCompositeKey.class)
public class DBReviewMetadata {

	@Id
	@Column(name = "review_id")
	private String reviewId;

	@Id
	@Column(name = "item")
	private String key;

	@Column
	private String value;

	public DBReviewMetadata() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DBReviewMetadata(String reviewId, String key, String value) {
		super();
		this.reviewId = reviewId;
		this.key = key;
		this.value = value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, reviewId, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBReviewMetadata other = (DBReviewMetadata) obj;
		return Objects.equals(key, other.key) && Objects.equals(reviewId, other.reviewId) && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "DBReviewMetadata [reviewId=" + reviewId + ", key=" + key + ", value=" + value + "]";
	}

}
