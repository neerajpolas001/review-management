package com.reviewservice.async.handlers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.reviewserivce.messeging.message.object.JobMessage;
import com.reviewservice.async.exceptions.ReviewServiceAsyncException;
import com.reviewservice.async.services.EmailService;
import com.reviewservice.async.utils.FileUtils;
import com.reviewservice.async.utils.ReportGenerator;
import com.reviewservice.businees.objects.Review;
import com.reviewservice.persistence.service.ReviewPersistenceService;
import com.reviewservice.utils.CollectionUtils;
import com.reviewservice.utils.StringUtils;

@Service
public class ReportGenerationHandler implements MessageHandler {

	@Value("${reviewservice.temp.directory.folders.emailservice}")
	private String directoryPath;

	@Value("#{new Long('${reviewservice.temp.directory.folders.emailservice.fileSize.KB}')}")
	private Long fileSizeInKB;

	// @Value("#{new Integer(${reviewservice.async.report.generation.page.size})}")
	private Integer pageSize = 100;

	@Autowired
	private ReviewPersistenceService reviewPersistenceService;

	@Autowired
	private EmailService emailService;

	@Override
	public void processMessage(JobMessage jobMessage) throws ReviewServiceAsyncException {
		this.validateJobMessage(jobMessage);
		HashMap<String, String> body = jobMessage.getBody();
		String time = body.get("time");
		body.remove("time");
		String email = body.get("email");
		body.remove("email");
		String userId = body.get("userId");
		try {
			if (body.size() > 1) {

				if (body.containsKey("branchId") && body.containsKey("sentiment")) {
					String branchId = checkAndGetValueInBody(body, "branchId");
					String sentiment = checkAndGetValueInBody(body, "sentiment");
					this.generateReportForUserIdAndBranchIdAndSentiment(userId, branchId, sentiment);
				}

				else if (body.containsKey("branchId")) {
					String branchId = checkAndGetValueInBody(body, "branchId");
					this.generateReportForUserIdAndBranchId(userId, branchId);
				}

				else if (body.containsKey("sentiment")) {
					String sentiment = checkAndGetValueInBody(body, "sentiment");
					this.generateReportForUserIdAndSentiment(userId, sentiment);
				}
			}

			else {
				this.generateReportForUserId(userId);
			}
			this.sendFilesInEmail(email, time, userId, body.get("branchId"), body.get("sentiment"));
		} catch (MailException | MessagingException e) {
			throw new ReviewServiceAsyncException(e.getMessage(), e);
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
			throw new ReviewServiceAsyncException(e.getMessage(), e);
		} finally {
			FileUtils.deleteAllFilesInFolder(directoryPath + "/" + userId);
		}
	}

	private void generateReportForUserId(String userId) throws ReviewServiceAsyncException, IllegalArgumentException, IllegalAccessException, IOException {
		int pageNo = 0;
		List<Review> reviews = null;
		ReportGenerator<Review> reportGenerator = new ReportGenerator<>(Review.class, userId, this.directoryPath);
		try {
			while (!CollectionUtils.isEmpty(reviews = this.reviewPersistenceService.getAllLatestReviews(userId, pageNo++, pageSize))) {
				getMegatDataForReview(reviews);
				reportGenerator.write(reviews, this.fileSizeInKB);
			}
		} finally {
			reportGenerator.close();
		}
	}

	private void generateReportForUserIdAndBranchId(String userId, String branchId)
			throws ReviewServiceAsyncException, IOException, IllegalArgumentException, IllegalAccessException {
		int pageNo = 0;
		List<Review> reviews = null;
		ReportGenerator<Review> reportGenerator = new ReportGenerator<>(Review.class, userId, this.directoryPath);
		try {
			while (!CollectionUtils.isEmpty(reviews = this.reviewPersistenceService.getAllLatestReviewsForBrachId(userId, branchId, pageNo++, pageSize))) {
				getMegatDataForReview(reviews);
				reportGenerator.write(reviews, this.fileSizeInKB);
			}
		} finally {
			reportGenerator.close();
		}
	}

	private void generateReportForUserIdAndSentiment(String userId, String sentiment)
			throws ReviewServiceAsyncException, IOException, IllegalArgumentException, IllegalAccessException {
		int pageNo = 0;

		List<Review> reviews = null;
		ReportGenerator<Review> reportGenerator = new ReportGenerator<>(Review.class, userId, this.directoryPath);
		try {
			while (!CollectionUtils.isEmpty(reviews = this.reviewPersistenceService.getAllLatestReviewsForSentiment(userId, sentiment, pageNo++, pageSize))) {
				getMegatDataForReview(reviews);
				reportGenerator.write(reviews, this.fileSizeInKB);
			}
		} finally {
			reportGenerator.close();
		}
	}

	private void generateReportForUserIdAndBranchIdAndSentiment(String userId, String branchId, String sentiment)
			throws ReviewServiceAsyncException, IOException, IllegalArgumentException, IllegalAccessException {
		int pageNo = 0;
		List<Review> reviews = null;
		ReportGenerator<Review> reportGenerator = new ReportGenerator<>(Review.class, userId, this.directoryPath);
		try {
			while (!CollectionUtils.isEmpty(reviews = this.reviewPersistenceService.getAllLatestReviewsForBranchIdAndSentiment(userId, branchId, sentiment, pageNo++, pageSize))) {
				getMegatDataForReview(reviews);
				reportGenerator.write(reviews, this.fileSizeInKB);
			}
		} finally {
			reportGenerator.close();
		}
	}

	private void getMegatDataForReview(List<Review> reviews) {
		for (Review review : reviews) {
			review.setMetaData((HashMap<String, String>) this.reviewPersistenceService.getMetaDataForReview(review.getId()));
		}
	}

	private void sendFilesInEmail(String email, String time, String userId, String branchId, String sentiment) throws ReviewServiceAsyncException, MailException, MessagingException {
		List<File> files = FileUtils.getAllFilesFromDirectory(directoryPath + "/" + userId);
		String subject = "Review Report requested on date : " + time;
		String text = "Report generation date:" + time 
				+ "\n for the criteria : " 
					+ "\n\t usrId : " + userId;
		if(!StringUtils.isEmpty(branchId))
				text += "\n\t branchId : " + branchId;
		if(!StringUtils.isEmpty(sentiment))
			text += "\n\t sentiment : " + sentiment;

		for (File file : files) {
			emailService.sendEmailWithAttachment(email, subject, text, file.getAbsolutePath());
		}
	}

	private void validateJobMessage(JobMessage jobMessage) throws ReviewServiceAsyncException {
		if (!jobMessage.getBody().containsKey("userId"))
			throw new ReviewServiceAsyncException("message body must have userId");
		String userId = jobMessage.getBody().get("userId");
		if (StringUtils.isEmptyOrBlank(userId))
			throw new ReviewServiceAsyncException("userId in the message body can not be null/Empty/Blank");
		if (!jobMessage.getBody().containsKey("time"))
			throw new ReviewServiceAsyncException("message body must have time");
		String time = jobMessage.getBody().get("time");
		if (StringUtils.isEmptyOrBlank(time))
			throw new ReviewServiceAsyncException("time in the message body can not be null/Empty/Blank");
		if (!jobMessage.getBody().containsKey("email"))
			throw new ReviewServiceAsyncException("message body must have email");
		String email = jobMessage.getBody().get("email");
		if (StringUtils.isEmptyOrBlank(email))
			throw new ReviewServiceAsyncException("email in the message body can not be null/Empty/Blank");
	}

	private String checkAndGetValueInBody(HashMap<String, String> body, String key) throws ReviewServiceAsyncException {
		String value = null;
		if (body.containsKey(key)) {
			value = body.get(key);
			if (StringUtils.isEmpty(key))
				throw new ReviewServiceAsyncException(key + " in the message body can not be null/Empty/Blank");
			return value;
		}
		return value;
	}
}
