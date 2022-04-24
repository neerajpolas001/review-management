package com.reviewservice.async.handlers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.reviewserivce.messeging.message.object.JobMessage;
import com.reviewservice.async.exceptions.ReviewServiceAsyncException;
import com.reviewservice.businees.objects.Review;
import com.reviewservice.businees.objects.SubscriptionTypes;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.service.ReviewPersistenceService;
import com.reviewservice.rest.reposeObjects.SentimentAnalysisResponse;
import com.reviewservice.utils.StringUtils;

@Service
public class ReviewMessageHandler implements MessageHandler {

	@Value("${sentiment.service.endpoint.basic}")
	private String sentimentSeviceEndPointBasic;

	@Value("${sentiment.service.endpoint.advance}")
	private String sentimentServiceEndpointAdvance;

	@Autowired
	private ReviewPersistenceService reviewPersistenceService;

	private RestTemplate restTemplate;

	public ReviewMessageHandler() {
		super();
		this.restTemplate = new RestTemplate();
	}

	public void setSentimentSeviceEndPointBasic(String sentimentSeviceEndPointBasic) {
		this.sentimentSeviceEndPointBasic = sentimentSeviceEndPointBasic;
	}

	public void setSentimentServiceEndpointAdvance(String sentimentServiceEndpointAdvance) {
		this.sentimentServiceEndpointAdvance = sentimentServiceEndpointAdvance;
	}

	@Override
	public void processMessage(JobMessage jobMessage) throws ReviewServiceAsyncException {
		try {
			validateJobMessage(jobMessage);
			String reviewId = jobMessage.getBody().get("reviewId");
			Review review = this.reviewPersistenceService.getReviewById(reviewId);
			SentimentAnalysisResponse response;
			if (SubscriptionTypes.SENTIMENT_ANALYSIS_BASIC.name().equals(jobMessage.getSubscriptionType()))
				response = getResponmseFromSentimentAnalysisService(this.sentimentSeviceEndPointBasic, review.getText());
			else if (SubscriptionTypes.SENTIMENT_ANALYSIS_ADVANCED.name().equals(jobMessage.getSubscriptionType()))
				response = getResponmseFromSentimentAnalysisService(this.sentimentServiceEndpointAdvance, review.getText());
			else
				throw new ReviewServiceAsyncException(ErrorCode.NOT_ACCEPTABLE, "sunscriptionType not supported : " + jobMessage.getSubscriptionType());
			this.reviewPersistenceService.updateReview(new Review.ReviewBuilder().id(reviewId).sentiment(response.getSentiment()).polarity(response.getPolarity()).build());

		} catch (URISyntaxException | PersistenceServiceException e) {
			throw new ReviewServiceAsyncException(e.getMessage(), e);
		}

	}

	private void validateJobMessage(JobMessage jobMessage) throws ReviewServiceAsyncException {
		if (!jobMessage.getBody().containsKey("reviewId"))
			throw new ReviewServiceAsyncException("message body must have reviewId");
		String reviewId = jobMessage.getBody().get("reviewId");
		if (StringUtils.isEmpty(reviewId))
			throw new ReviewServiceAsyncException("reviewId in the message body can not be null/Empty");
		if (!jobMessage.getBody().containsKey("userId"))
			throw new ReviewServiceAsyncException("message body must have userId");
		String userId = jobMessage.getBody().get("reviewId");
		if (StringUtils.isEmpty(userId))
			throw new ReviewServiceAsyncException("userId in the message body can not be null/Empty");
	}

	private SentimentAnalysisResponse getResponmseFromSentimentAnalysisService(String uriString, String text) throws URISyntaxException {
		URI uri = getURI(uriString);
		Map<String, String> map = new HashMap<>();
		map.put("text", text);
		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(map);
		ResponseEntity<SentimentAnalysisResponse> responseEntity = this.restTemplate.exchange(uri, HttpMethod.POST, httpEntity, SentimentAnalysisResponse.class);
		SentimentAnalysisResponse response = responseEntity.getBody();
		return response;
	}

	private URI getURI(String uri) throws URISyntaxException {
		return new URI(uri);
	}

}
