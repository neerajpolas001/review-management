package com.reviewservice.async;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.reviewserivce.messeging.message.object.JobMessage;
import com.reviewservice.async.exceptions.ReviewServiceAsyncException;
import com.reviewservice.async.handlers.ReportGenerationHandler;
import com.reviewservice.businees.objects.SubscriptionTypes;

@SpringBootApplication
@ComponentScan(basePackages = { "com.*" })
@EnableJpaRepositories(basePackages = { "com.*" })
@EntityScan(basePackages = { "com.*" })
public class ReviewServiceAsyncApplication {

	@Autowired
	private ReportGenerationHandler handler;

	public static void main(String[] args) {
		SpringApplication.run(ReviewServiceAsyncApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void triggerEmail() throws ReviewServiceAsyncException {

		JobMessage message = new JobMessage();
		message.setSubscriptionType(SubscriptionTypes.REPORT_GENERATION.name());
		HashMap<String, String> body = new HashMap<>();
		body.put("userId", "c8aba65e-f1d0-488e-8e35-98741583831c");
		body.put("email", "neeraj.polas@gmail.com");
		Date date = new Date();
		body.put("time", date.toString());
		message.setBody(body);
		handler.processMessage(message);
	}

}
