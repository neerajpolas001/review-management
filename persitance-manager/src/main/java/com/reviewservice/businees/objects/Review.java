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

	@NotBlank(message = "city can not be null/empty")
	private String city;

	@NotBlank(message = "state can not be null/empty")
	private String state;

	@NotBlank(message = "country can not be null/empty")
	private String country;

	private HashMap<String, String> metaData;

	public Review() {
		super();
	}

	public Review(String id, String userId, String text, String branchId, String branchName, String orderType, Date dateCreated, Date dateModiefied, String rating, int votes,
			String city, String state, String country) {
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
	}

	private Review(ReviewBuilder builder) {
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
		this.metaData = builder.metaData;
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

	public HashMap<String, String> getMetaData() {
		return metaData;
	}

	public void setMetaData(HashMap<String, String> metaData) {
		this.metaData = metaData;
	}

	@Override
	public int hashCode() {
		return Objects.hash(branchId, branchName, city, country, dateCreated, dateModiefied, id, metaData, orderType, rating, state, text, userId, votes);
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
				&& Objects.equals(id, other.id) && Objects.equals(metaData, other.metaData) && Objects.equals(orderType, other.orderType) && Objects.equals(rating, other.rating)
				&& Objects.equals(state, other.state) && Objects.equals(text, other.text) && Objects.equals(userId, other.userId) && votes == other.votes;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", userId=" + userId + ", text=" + text + ", branchId=" + branchId + ", branchName=" + branchName + ", orderType=" + orderType
				+ ", dateCreated=" + dateCreated + ", dateModiefied=" + dateModiefied + ", rating=" + rating + ", votes=" + votes + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", metaData=" + metaData + "]";
	}

	public static class ReviewBuilder {
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

		private HashMap<String, String> metaData;

		public ReviewBuilder() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ReviewBuilder id(String id) {
			this.id = id;
			return this;
		}

		public ReviewBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}

		public ReviewBuilder text(String text) {
			this.text = text;
			return this;
		}

		public ReviewBuilder branchId(String branchId) {
			this.branchId = branchId;
			return this;
		}

		public ReviewBuilder branchName(String branchName) {
			this.branchName = branchName;
			return this;
		}

		public ReviewBuilder orderType(String orderType) {
			this.orderType = orderType;
			return this;
		}

		public ReviewBuilder dateCreated(Date dateCreated) {
			this.dateCreated = dateCreated;
			return this;
		}

		public ReviewBuilder dateModiefied(Date dateModiefied) {
			this.dateModiefied = dateModiefied;
			return this;
		}

		public ReviewBuilder rating(String rating) {
			this.rating = rating;
			return this;
		}

		public ReviewBuilder votes(int votes) {
			this.votes = votes;
			return this;
		}

		public ReviewBuilder city(String city) {
			this.city = city;
			return this;
		}

		public ReviewBuilder state(String state) {
			this.state = state;
			return this;
		}

		public ReviewBuilder country(String country) {
			this.country = country;
			return this;
		}

		public ReviewBuilder metaData(HashMap<String, String> metaData) {
			this.metaData = metaData;
			return this;
		}

		public Review build() {

			return new Review(this);
		}

	}

}
