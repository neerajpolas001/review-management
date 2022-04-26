package com.reviewservice.service.objects;

import java.util.Date;
import java.util.HashMap;

import org.springframework.jms.core.JmsTemplate;

import com.reviewserivce.messeging.message.object.JobMessage;
import com.reviewservice.businees.objects.SubscriptionTypes;
import com.reviewservice.businees.objects.User;
import com.reviewservice.rest.exceptions.ReviewManagementServiceException;
import com.reviewservice.utils.StringUtils;

public class ReportGenerationMessage {

	private User user;

	private Date date;

	private SubscriptionTypes subscriptionType;

	private String branchId;

	private String sentiment;

	private JobMessage jobMessage;

	public ReportGenerationMessage() {
		super();
	}

	public ReportGenerationMessage(Builder builder) {
		super();
		this.user = builder.user;
		this.date = builder.date;
		this.subscriptionType = builder.subscriptionType;
		this.branchId = builder.branchId;
		this.sentiment = builder.sentiment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SubscriptionTypes getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(SubscriptionTypes subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	@Override
	public String toString() {
		return "ReportGenerationRequestHandler [user=" + user + ", date=" + date + ", subscriptionType=" + subscriptionType + ", branchId=" + branchId + ", sentiment=" + sentiment
				+ "]";
	}

	public ReportGenerationMessage buildMessage() {
		HashMap<String, String> body = new HashMap<>();
		body.put("userId", this.user.getId());
		body.put("email", this.user.getEmail());
		body.put("time", this.date.toString());
		if (!StringUtils.isEmptyOrBlank(this.branchId))
			body.put("branchId", this.branchId);
		if (!StringUtils.isEmptyOrBlank(this.sentiment))
			body.put("sentiment", this.sentiment);
		this.jobMessage = new JobMessage(this.subscriptionType.name(), body);
		return this;
	}

	public void sendMessage(JmsTemplate jmsTemplate, String queueName) throws ReviewManagementServiceException {
		try {
			jmsTemplate.convertAndSend(queueName, jobMessage);
		} catch (Exception e) {
			throw new ReviewManagementServiceException("Error occured while sending job into messageing queue '" + queueName + "'");
		}
	}

	public static class Builder {
		private User user;

		private Date date;

		private SubscriptionTypes subscriptionType;

		private String branchId;

		private String sentiment;

		public Builder(User user, Date date, SubscriptionTypes subscriptionType) {
			super();
			this.user = user;
			this.date = date;
			this.subscriptionType = subscriptionType;
		}

//		public Builder user(User user) {
//			this.user = user;
//			return this;
//		}
//
//		public Builder date(Date date) {
//			this.date = date;
//			return this;
//		}
//
//		public Builder subscriptionType(SubscriptionTypes subscriptionType) {
//			this.subscriptionType = subscriptionType;
//			return this;
//		}

		public Builder branchId(String branchId) {
			this.branchId = branchId;
			return this;
		}

		public Builder sentiment(String sentiment) {
			this.sentiment = sentiment;
			return this;
		}

		public ReportGenerationMessage build() {
			return new ReportGenerationMessage(this);
		}
	}

}
