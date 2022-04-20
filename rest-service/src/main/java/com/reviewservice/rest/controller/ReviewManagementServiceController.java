package com.reviewservice.rest.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reviewservice.businees.objects.Review;
import com.reviewservice.exceptions.ErrorCode;
import com.reviewservice.exceptions.PersistenceServiceException;
import com.reviewservice.persistence.service.ReviewPersistenceService;
import com.reviewservice.rest.exceptions.ReviewManagementServiceException;
import com.reviewservice.rest.exceptions.UserServiceException;
import com.reviewservice.services.SessionService;

@RestController
@RequestMapping("review-management")
@EnableTransactionManagement
public class ReviewManagementServiceController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ReviewPersistenceService reviewPersistenceService;

	@PostMapping("/reviews/post-reviw")
	public Review saveReview(@RequestHeader(name = "sessionId", required = true) String sessionId, @RequestBody @Valid Review review)
			throws PersistenceServiceException, UserServiceException {
		String userId = sessionService.validateSession(sessionId).getUserId();
		if (!userId.equals(review.getUserId()))
			new ReviewManagementServiceException(ErrorCode.BAD_REQUEST, "userId in Review does not belong to the currrent session, please relogin with appropriate user");
		return reviewPersistenceService.saveReview(review);
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
