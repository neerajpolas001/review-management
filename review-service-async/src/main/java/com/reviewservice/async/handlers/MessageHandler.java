package com.reviewservice.async.handlers;

import com.reviewserivce.messeging.message.object.JobMessage;
import com.reviewservice.async.exceptions.ReviewServiceAsyncException;

public interface MessageHandler {

	public void processMessage(JobMessage jobMessage) throws ReviewServiceAsyncException;

}
