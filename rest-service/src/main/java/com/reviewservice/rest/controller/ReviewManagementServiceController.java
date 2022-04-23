package com.reviewservice.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reviewserivce.messeging.message.object.JobMessage;
import com.reviewservice.businees.objects.Review;
import com.reviewservice.businees.objects.Subscription;
import com.reviewservice.businees.objects.SubscriptionTypes;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.service.ReviewPersistenceService;
import com.reviewservice.rest.exceptions.ReviewManagementServiceException;
import com.reviewservice.rest.exceptions.UserServiceException;
import com.reviewservice.services.SessionService;
import com.reviewservice.services.SubscriptionManagementService;
import com.reviewservice.utils.StringUtils;

@RestController
@RequestMapping("review-management")
@EnableTransactionManagement
public class ReviewManagementServiceController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ReviewPersistenceService reviewPersistenceService;

	@Autowired
	private SubscriptionManagementService subscriptionManagementService;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${jms.queue}")
	private String queueName;

	@PostMapping("/reviews/post-reviw")
	public Review saveReview(@RequestHeader(name = "sessionId", required = true) String sessionId, @RequestBody @Valid Review review)
			throws PersistenceServiceException, UserServiceException, ReviewManagementServiceException {
//		if (StringUtils.isEmpty(review.getUserId()))
//			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userId in review can not be null/empty");
//		if (StringUtils.isEmpty(review.getText()))
//			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "text of the review can not null/empty");
		String userId = sessionService.validateSession(sessionId).getUserId();
		if (!userId.equals(review.getUserId()))
			new ReviewManagementServiceException(ErrorCode.BAD_REQUEST, "userId in Review does not belong to the currrent session, please relogin with appropriate user");
		Review reviewRespose = reviewPersistenceService.saveReview(review);
		List<Subscription> subscriptions = subscriptionManagementService.getAllSubscriptionsForUser(userId);
		Subscription subscription = subscriptions.stream()
				.filter(s -> SubscriptionTypes.SENTIMENT_ANALYSIS_BASIC.equalTo(s.getName()) || SubscriptionTypes.SENTIMENT_ANALYSIS_ADVANCED.equalTo(s.getName()))
				.collect(Collectors.toList()).get(0);
		HashMap<String, String> body = new HashMap<>();
		body.put("userId", userId);
		body.put("reviewId", reviewRespose.getId());
		this.sendJMSMessage(new JobMessage(subscription.getName(), body));
		return reviewRespose;
	}

	private void sendJMSMessage(JobMessage jobMessage) throws ReviewManagementServiceException {
		try {
			this.jmsTemplate.convertAndSend(queueName, jobMessage);
		} catch (Exception e) {
			// TODO: handle exception and add into error queue
			throw new ReviewManagementServiceException("Error occured while sending job into messageing queue '" + this.queueName + "'");
		}

	}

	@GetMapping("/test-jms")
	public void testJMS() {

		// jmsTemplate.convertAndSend(queueName, text);
		HashMap<String, String> map = new HashMap<>();
		map.put("reviewId", "dsdsdsdsdsdreviewId");
		jmsTemplate.convertAndSend(queueName, new JobMessage(SubscriptionTypes.SENTIMENT_ANALYSIS_BASIC.name(), map));
	}

	@GetMapping("/reviews/test")
	public Review saveReview() {

		Review review = new Review();
		HashMap<String, String> map = new HashMap<>();
		map.put("Key", "value");
		review.setMetaData(map);
		return review;
	}

}
