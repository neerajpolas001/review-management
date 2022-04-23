package com.reviewserivce.persitance.db.objects;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
public class DBReview {

	@Id
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(length = 500)
	private String text;

	@Column(name = "branch_id")
	private String branchId;

	@Column(name = "branch_name")
	private String branchName;

	@Column(length = 50)
	private String orderType;

	@Column(name = "date_created")
	private Date dateCreated;

	@Column(name = "date_modified")
	private Date dateModiefied;

	@Column(length = 10)
	private String rating;

	@Column
	private int votes;

	@Column
	private String city;

	@Column
	private String state;

	@Column
	private String country;

	@Column
	private String sentiment;

	@Column
	private double polarity;

	public DBReview() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DBReview(String id, String userId, String text, String branchId, String branchName, String orderType, Date dateCreated, Date dateModiefied, String rating, int votes,
			String city, String state, String country, String sentiment, double polarity) {
		super();
		this.id = id;
		this.userId = userId;
		this.text = text;
		this.branchId = branchId;
		this.branchName = branchName;
		this.orderType = orderType;
		this.dateCreated = dateCreated;
		this.dateModiefied = dateModiefied;
		this.rating = rating;
		this.votes = votes;
		this.city = city;
		this.state = state;
		this.country = country;
		this.sentiment = sentiment;
		this.polarity = polarity;
	}

	private DBReview(DBReviewBuilder builder) {
		super();
		this.id = builder.id;
		this.userId = builder.userId;
		this.text = builder.text;
		this.branchId = builder.branchId;
		this.branchName = builder.branchName;
		this.orderType = builder.orderType;
		this.dateCreated = builder.dateCreated;
		this.dateModiefied = builder.dateModiefied;
		this.rating = builder.rating;
		this.votes = builder.votes;
		this.city = builder.city;
		this.state = builder.state;
		this.country = builder.country;
		this.sentiment = builder.sentiment;
		this.polarity = builder.polarity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModiefied() {
		return dateModiefied;
	}

	public void setDateModiefied(Date dateModiefied) {
		this.dateModiefied = dateModiefied;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public double getPolarity() {
		return polarity;
	}

	public void setPolarity(double polarity) {
		this.polarity = polarity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(branchId, branchName, city, country, dateCreated, dateModiefied, id, orderType, rating, state, text, userId, votes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBReview other = (DBReview) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(branchName, other.branchName) && Objects.equals(city, other.city)
				&& Objects.equals(country, other.country) && Objects.equals(dateCreated, other.dateCreated) && Objects.equals(dateModiefied, other.dateModiefied)
				&& Objects.equals(id, other.id) && Objects.equals(orderType, other.orderType) && Objects.equals(rating, other.rating) && Objects.equals(state, other.state)
				&& Objects.equals(text, other.text) && Objects.equals(userId, other.userId) && votes == other.votes;
	}

	@Override
	public String toString() {
		return "DBReview [id=" + id + ", userId=" + userId + ", text=" + text + ", branchId=" + branchId + ", branchName=" + branchName + ", orderType=" + orderType
				+ ", dateCreated=" + dateCreated + ", dateModiefied=" + dateModiefied + ", rating=" + rating + ", votes=" + votes + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", sentiment=" + sentiment + ", polarity=" + polarity + "]";
	}

	public static class DBReviewBuilder {
		private String id;

		private String userId;

		private String text;

		private String branchId;

		private String branchName;

		private String orderType;

		private Date dateCreated;

		private Date dateModiefied;

		private String rating;

		private int votes;

		private String city;

		private String state;

		private String country;

		private String sentiment;

		private double polarity;

		public DBReviewBuilder() {
			super();
			// TODO Auto-generated constructor stub
		}

		public DBReviewBuilder id(String id) {
			this.id = id;
			return this;
		}

		public DBReviewBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}

		public DBReviewBuilder text(String text) {
			this.text = text;
			return this;
		}

		public DBReviewBuilder branchId(String branchId) {
			this.branchId = branchId;
			return this;
		}

		public DBReviewBuilder branchName(String branchName) {
			this.branchName = branchName;
			return this;
		}

		public DBReviewBuilder orderType(String orderType) {
			this.orderType = orderType;
			return this;
		}

		public DBReviewBuilder dateCreated(Date dateCreated) {
			this.dateCreated = dateCreated;
			return this;
		}

		public DBReviewBuilder dateModiefied(Date dateModiefied) {
			this.dateModiefied = dateModiefied;
			return this;
		}

		public DBReviewBuilder rating(String rating) {
			this.rating = rating;
			return this;
		}

		public DBReviewBuilder votes(int votes) {
			this.votes = votes;
			return this;
		}

		public DBReviewBuilder city(String city) {
			this.city = city;
			return this;
		}

		public DBReviewBuilder state(String state) {
			this.state = state;
			return this;
		}

		public DBReviewBuilder country(String country) {
			this.country = country;
			return this;
		}

		public DBReviewBuilder sentiment(String sentiment) {
			this.sentiment = sentiment;
			return this;
		}

		public DBReviewBuilder polarity(double polarity) {
			this.polarity = polarity;
			return this;
		}

		public DBReview build() {

			return new DBReview(this);
		}

	}

}
