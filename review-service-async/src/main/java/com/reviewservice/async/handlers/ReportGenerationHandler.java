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

			} else {
				this.generateReportForUserId(userId);
			}
			sendFilesInEmail(userId, email, time);
		} catch (MailException | ReviewServiceAsyncException | MessagingException e) {
			throw new ReviewServiceAsyncException(e.getMessage(), e);
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
			throw new ReviewServiceAsyncException(e.getMessage(), e);
		}

	}

	private void generateReportForUserId(String userId) throws ReviewServiceAsyncException, IllegalArgumentException, IllegalAccessException, IOException {
		int pageNo = 0;
		int pageSize = 100;
		List<Review> reviews = null;
		ReportGenerator<Review> reportGenerator = new ReportGenerator<>(Review.class, userId, this.directoryPath);
		try {
			while (!CollectionUtils.isEmpty(reviews = this.reviewPersistenceService.getAllLatestReviews(userId, pageNo++, pageSize))) {
				for (Review review : reviews) {
					review.setMetaData((HashMap<String, String>) this.reviewPersistenceService.getMetaDataForReview(review.getId()));
				}
				reportGenerator.write(reviews, this.fileSizeInKB);
			}
		} finally {
			reportGenerator.close();
		}
	}

	private void sendFilesInEmail(String userId, String email, String time) throws ReviewServiceAsyncException, MailException, MessagingException {
		List<File> files = FileUtils.getAllFilesFromDirectory(directoryPath + "/" + userId);
		String subject = "Review Report requested on date : " + time;
		String text = "usrId : " + userId + "\n" + "time  : " + time + "\n";

		for (File file : files) {
			emailService.sendEmailWithAttachment(email, subject, text, file.getAbsolutePath());
		}
	}

	private void validateJobMessage(JobMessage jobMessage) throws ReviewServiceAsyncException {
		if (!jobMessage.getBody().containsKey("userId"))
			throw new ReviewServiceAsyncException("message body must have userId");
		String userId = jobMessage.getBody().get("userId");
		if (StringUtils.isEmpty(userId))
			throw new ReviewServiceAsyncException("userId in the message body can not be null/Empty");
		if (!jobMessage.getBody().containsKey("time"))
			throw new ReviewServiceAsyncException("message body must have time");
		String time = jobMessage.getBody().get("time");
		if (StringUtils.isEmpty(time))
			throw new ReviewServiceAsyncException("time in the message body can not be null/Empty");
		if (!jobMessage.getBody().containsKey("email"))
			throw new ReviewServiceAsyncException("message body must have email");
		String email = jobMessage.getBody().get("email");
		if (StringUtils.isEmpty(email))
			throw new ReviewServiceAsyncException("email in the message body can not be null/Empty");
	}

}
