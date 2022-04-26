package com.reviewservice.rest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviewserivce.messeging.message.object.JobMessage;
import com.reviewservice.businees.objects.Review;
import com.reviewservice.businees.objects.Subscription;
import com.reviewservice.businees.objects.SubscriptionTypes;
import com.reviewservice.businees.objects.User;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.service.ReviewPersistenceService;
import com.reviewservice.persistence.service.UserPersistenceService;
import com.reviewservice.rest.exceptions.ReviewManagementServiceException;
import com.reviewservice.rest.exceptions.UserServiceException;
import com.reviewservice.service.objects.ReportGenerationMessage;
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
	private UserPersistenceService userPersistenceService;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${jms.queue}")
	private String queueName;

	@PostMapping("/reviews/post-review")
	public Review saveReview(@RequestHeader(name = "sessionId", required = true) String sessionId, @RequestBody @Valid Review review)
			throws PersistenceServiceException, UserServiceException, ReviewManagementServiceException {
		if (StringUtils.isEmptyOrBlank(review.getUserId()))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "userId in review can not be null/empty/blank");
		if (StringUtils.isEmptyOrBlank(review.getText()))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "text of the review can not null/empty/blank");
		String userId = sessionService.validateSession(sessionId).getUserId();
		if (!userId.equals(review.getUserId()))
			new ReviewManagementServiceException(ErrorCode.BAD_REQUEST, "userId in Review does not belong to the currrent session, please relogin with appropriate user");
		Review reviewRespose = reviewPersistenceService.saveReview(review);
		List<Subscription> subscriptions = subscriptionManagementService.getAllSubscriptionsForUser(userId);
		this.handleSentimentAnalysisSubscriptions(userId, reviewRespose, subscriptions);
		return reviewRespose;
	}

	private void handleSentimentAnalysisSubscriptions(String userId, Review reviewRespose, List<Subscription> subscriptions) throws ReviewManagementServiceException {
		Optional<Subscription> optional = subscriptions.stream()
				.filter(s -> SubscriptionTypes.SENTIMENT_ANALYSIS_BASIC.equalTo(s.getName()) || SubscriptionTypes.SENTIMENT_ANALYSIS_ADVANCED.equalTo(s.getName())).findAny();
		if (optional.isPresent()) {
			Subscription subscription = optional.get();
			HashMap<String, String> body = new HashMap<>();
			body.put("userId", userId);
			body.put("reviewId", reviewRespose.getId());
			this.sendJMSMessage(new JobMessage(subscription.getName(), body));
		}
	}

	private void sendJMSMessage(JobMessage jobMessage) throws ReviewManagementServiceException {
		try {
			this.jmsTemplate.convertAndSend(queueName, jobMessage);
		} catch (Exception e) {
			// TODO: handle exception and add into error queue
			throw new ReviewManagementServiceException("Error occured while sending job into messageing queue '" + this.queueName + "'");
		}

	}

	@GetMapping("/reviews")
	public List<Review> getAllLatestReviews(@RequestHeader(name = "sessionId", required = true) String sessionId, @RequestParam int pageNo, @RequestParam int pageSize)
			throws PersistenceServiceException, UserServiceException {
		String userId = this.sessionService.validateSession(sessionId).getUserId();
		return this.reviewPersistenceService.getAllLatestReviews(userId, pageNo, pageSize);
	}

	@GetMapping("{branchId}/reviews")
	public List<Review> getAllLatestReviewsForBranch(@RequestHeader(name = "sessionId", required = true) String sessionId, @PathVariable(required = true) String branchId,
			@RequestParam int pageNo, @RequestParam int pageSize) throws PersistenceServiceException, UserServiceException {
		if (StringUtils.isEmptyOrBlank(branchId))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "branchId in review can not be null/empty/blank");
		String userId = this.sessionService.validateSession(sessionId).getUserId();
		return this.reviewPersistenceService.getAllLatestReviewsForBrachId(userId, branchId, pageNo, pageSize);
	}

	@GetMapping("/reviews/{sentiment}")
	public List<Review> getAllLatestReviewsForSentiment(@RequestHeader(name = "sessionId", required = true) String sessionId, @PathVariable(required = true) String sentiment,
			@RequestParam int pageNo, @RequestParam int pageSize) throws PersistenceServiceException, UserServiceException {
		if (StringUtils.isEmptyOrBlank(sentiment))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "sentiment in review can not be null/empty/blank");
		sentiment = sentiment.toLowerCase();
		String userId = this.sessionService.validateSession(sessionId).getUserId();
		return this.reviewPersistenceService.getAllLatestReviewsForSentiment(userId, sentiment, pageNo, pageSize);
	}

	@GetMapping("{branchId}/reviews/{sentiment}")
	public List<Review> getAllLatestReviewsForSentiment(@RequestHeader(name = "sessionId", required = true) String sessionId, @PathVariable(required = true) String branchId,
			@PathVariable(required = true) String sentiment, @RequestParam int pageNo, @RequestParam int pageSize) throws PersistenceServiceException, UserServiceException {
		if (StringUtils.isEmptyOrBlank(branchId))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "branchId in review can not be null/empty/blank");
		if (StringUtils.isEmptyOrBlank(sentiment))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "sentiment in review can not be null/empty/blank");
		sentiment = sentiment.toLowerCase();
		String userId = this.sessionService.validateSession(sessionId).getUserId();
		return this.reviewPersistenceService.getAllLatestReviewsForBranchIdAndSentiment(userId, branchId, sentiment, pageNo, pageSize);
	}

	@GetMapping("reviews/report")
	public Map<String, String> getReportForUser(@RequestHeader(name = "sessionId", required = true) String sessionId)
			throws PersistenceServiceException, UserServiceException, ReviewManagementServiceException {
		String userId = this.sessionService.validateSession(sessionId).getUserId();
		return handleReportGenerationRequest(userId, null, null);
	}

	@GetMapping("{branchId}/reviews/report")
	public Map<String, String> getReportForUserAndBranchId(@RequestHeader(name = "sessionId", required = true) String sessionId, @PathVariable(required = true) String branchId)
			throws PersistenceServiceException, UserServiceException, ReviewManagementServiceException {
		if (StringUtils.isEmptyOrBlank(branchId))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "branchId in review can not be null/empty/blank");
		if (!this.reviewPersistenceService.checkIfExists(new Review.Builder().branchId(branchId).build()))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "No branch with branchId = '" + branchId + "' exists");
		String userId = this.sessionService.validateSession(sessionId).getUserId();
		return handleReportGenerationRequest(userId, branchId, null);
	}

	@GetMapping("reviews/{sentiment}/report")
	public Map<String, String> getReportForUserAndSentiment(@RequestHeader(name = "sessionId", required = true) String sessionId, @PathVariable(required = true) String sentiment)
			throws PersistenceServiceException, UserServiceException, ReviewManagementServiceException {
		if (StringUtils.isEmptyOrBlank(sentiment))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "sentiment in review can not be null/empty/blank");
		sentiment = sentiment.toLowerCase();
		String userId = this.sessionService.validateSession(sessionId).getUserId();
		return handleReportGenerationRequest(userId, null, sentiment);
	}

	@GetMapping("{branchId}/reviews/{sentiment}/report")
	public Map<String, String> getReportForUserAndBranchIdAndSentiment(@RequestHeader(name = "sessionId", required = true) String sessionId,
			@PathVariable(required = true) String branchId, @PathVariable(required = true) String sentiment)
			throws PersistenceServiceException, UserServiceException, ReviewManagementServiceException {
		if (StringUtils.isEmptyOrBlank(branchId))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "branchId in review can not be null/empty/blank");
		if (StringUtils.isEmptyOrBlank(sentiment))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "sentiment in review can not be null/empty/blank");
		if (!this.reviewPersistenceService.checkIfExists(new Review.Builder().branchId(branchId).build()))
			throw new PersistenceServiceException(ErrorCode.BAD_REQUEST, "No branch with branchId = '" + branchId + "' exists");
		sentiment = sentiment.toLowerCase();
		String userId = this.sessionService.validateSession(sessionId).getUserId();
		return handleReportGenerationRequest(userId, branchId, sentiment);
	}

	private Map<String, String> handleReportGenerationRequest(String userId, String branchId, String sentiment)
			throws PersistenceServiceException, UserServiceException, ReviewManagementServiceException {
		Date time = new Date();
		validateSubscriptionForUser(userId, SubscriptionTypes.REPORT_GENERATION);
		User user = this.userPersistenceService.getUserById(userId);
		ReportGenerationMessage reportGenerationMessage = new ReportGenerationMessage.Builder(user, time, SubscriptionTypes.REPORT_GENERATION).branchId(branchId)
				.sentiment(sentiment).build();
		reportGenerationMessage.buildMessage().sendMessage(jmsTemplate, queueName);
		return reportGenerationResponse(time);
	}

	private void validateSubscriptionForUser(String userId, SubscriptionTypes subscriptionType) throws PersistenceServiceException, UserServiceException {
		List<Subscription> subscriptions = this.subscriptionManagementService.getAllSubscriptionsForUser(userId);
		Optional<Subscription> optional = subscriptions.stream().filter(s -> subscriptionType.name().equals(s.getName())).findAny();
		if (!optional.isPresent())
			throw new UserServiceException(ErrorCode.FORDBIDDEN, "The user is not subscribed to the feature = '" + subscriptionType.name() + "'");
	}

	private Map<String, String> reportGenerationResponse(Date time) {
		Map<String, String> map = new HashMap<>();
		map.put("Message", "Report request generated. Reports will be mailed shortly at user's registered email address");
		map.put("Time of request", time.toString());
		return map;
	}

	@GetMapping("/test-jms")
	public void testJMS() {
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
