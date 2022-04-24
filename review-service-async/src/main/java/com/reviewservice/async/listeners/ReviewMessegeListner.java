package com.reviewservice.async.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.reviewserivce.messeging.message.object.JobMessage;
import com.reviewservice.async.exceptions.ReviewServiceAsyncException;
import com.reviewservice.async.handlers.MessageHandler;
import com.reviewservice.async.handlers.ReviewMessageHandler;
import com.reviewservice.businees.objects.SubscriptionTypes;

@Service
public class ReviewMessegeListner {

	private Logger logger = LoggerFactory.getLogger(ReviewMessegeListner.class);

	/*
	 * @JmsListener(destination = "${jms.queue}") public void receiveMessage(String
	 * message){ log.info("Received message: " + message); }
	 */
	@Autowired
	@Qualifier("reviewMessageHandler")
	private MessageHandler messageHandler;

	@JmsListener(destination = "${jms.queue}")
	public void receiveMessage(JobMessage message) {
		try {
			if (SubscriptionTypes.SENTIMENT_ANALYSIS_BASIC.name().equals(message.getSubscriptionType())
					|| SubscriptionTypes.SENTIMENT_ANALYSIS_ADVANCED.name().equals(message.getSubscriptionType())) {
				this.messageHandler.processMessage(message);
			}
		} catch (ReviewServiceAsyncException e) {
			this.logger.error(e.getMessage(), e);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}
	}
}
