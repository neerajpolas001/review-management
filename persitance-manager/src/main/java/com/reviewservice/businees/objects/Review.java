package com.reviewservice.businees.objects;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

public class Review {

	private String id;

	@NotBlank(message = "userId can not be null/empty")
	private String userId;

	@NotBlank(message = "review text can not be null/empty ")
	private String text;

	private String branchId = "noBranch";

	private String branchName = "noBranch";

	private String orderType = "NA";

	private Date dateCreated;

	private Date dateModiefied;

	private String rating = "NA";

	private int votes;

	@NotBlank(message = "reviewerName can not be null/empty")
	private String reviewerName;

	@NotBlank(message = "reviewerEmail can not be null/empty")
	private String reviewerEmail;

	@NotBlank(message = "city can not be null/empty")
	private String city;

	@NotBlank(message = "state can not be null/empty")
	private String state;

	@NotBlank(message = "country can not be null/empty")
	private String country;

	private String sentiment;

	private double polarity;

	private HashMap<String, String> metaData;

	public Review() {
		super();
	}

	private Review(Builder builder) {
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
		this.reviewerName = builder.reviewerName;
		this.reviewerEmail = builder.reviewerEmail;
		this.city = builder.city;
		this.state = builder.state;
		this.country = builder.country;
		this.metaData = builder.metaData;
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

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public String getReviewerEmail() {
		return reviewerEmail;
	}

	public void setReviewerEmail(String reviewerEmail) {
		this.reviewerEmail = reviewerEmail;
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

	public HashMap<String, String> getMetaData() {
		return metaData;
	}

	public void setMetaData(HashMap<String, String> metaData) {
		this.metaData = metaData;
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
		return Objects.hash(branchId, branchName, city, country, dateCreated, dateModiefied, id, metaData, orderType, polarity, rating, reviewerEmail, reviewerName, sentiment,
				state, text, userId, votes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(branchName, other.branchName) && Objects.equals(city, other.city)
				&& Objects.equals(country, other.country) && Objects.equals(dateCreated, other.dateCreated) && Objects.equals(dateModiefied, other.dateModiefied)
				&& Objects.equals(id, other.id) && Objects.equals(metaData, other.metaData) && Objects.equals(orderType, other.orderType)
				&& Double.doubleToLongBits(polarity) == Double.doubleToLongBits(other.polarity) && Objects.equals(rating, other.rating)
				&& Objects.equals(reviewerEmail, other.reviewerEmail) && Objects.equals(reviewerName, other.reviewerName) && Objects.equals(sentiment, other.sentiment)
				&& Objects.equals(state, other.state) && Objects.equals(text, other.text) && Objects.equals(userId, other.userId) && votes == other.votes;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", userId=" + userId + ", text=" + text + ", branchId=" + branchId + ", branchName=" + branchName + ", orderType=" + orderType
				+ ", dateCreated=" + dateCreated + ", dateModiefied=" + dateModiefied + ", rating=" + rating + ", votes=" + votes + ", reviewerName=" + reviewerName
				+ ", reviewerEmail=" + reviewerEmail + ", city=" + city + ", state=" + state + ", country=" + country + ", sentiment=" + sentiment + ", polarity=" + polarity
				+ ", metaData=" + metaData + "]";
	}

	public static class Builder {
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

		private String reviewerName;

		private String reviewerEmail;

		private String city;

		private String state;

		private String country;

		private String sentiment;

		private double polarity;

		private HashMap<String, String> metaData;

		public Builder() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder userId(String userId) {
			this.userId = userId;
			return this;
		}

		public Builder text(String text) {
			this.text = text;
			return this;
		}

		public Builder branchId(String branchId) {
			this.branchId = branchId;
			return this;
		}

		public Builder branchName(String branchName) {
			this.branchName = branchName;
			return this;
		}

		public Builder orderType(String orderType) {
			this.orderType = orderType;
			return this;
		}

		public Builder dateCreated(Date dateCreated) {
			this.dateCreated = dateCreated;
			return this;
		}

		public Builder dateModiefied(Date dateModiefied) {
			this.dateModiefied = dateModiefied;
			return this;
		}

		public Builder rating(String rating) {
			this.rating = rating;
			return this;
		}

		public Builder votes(int votes) {
			this.votes = votes;
			return this;
		}

		public Builder reviewerName(String reviewerName) {
			this.reviewerName = reviewerName;
			return this;
		}

		public Builder reviewerEmail(String reviewerEmail) {
			this.reviewerEmail = reviewerEmail;
			return this;
		}

		public Builder city(String city) {
			this.city = city;
			return this;
		}

		public Builder state(String state) {
			this.state = state;
			return this;
		}

		public Builder country(String country) {
			this.country = country;
			return this;
		}

		public Builder metaData(HashMap<String, String> metaData) {
			this.metaData = metaData;
			return this;
		}

		public Builder sentiment(String sentiment) {
			this.sentiment = sentiment;
			return this;
		}

		public Builder polarity(double polarity) {
			this.polarity = polarity;
			return this;
		}

		public Review build() {

			return new Review(this);
		}

	}

}
