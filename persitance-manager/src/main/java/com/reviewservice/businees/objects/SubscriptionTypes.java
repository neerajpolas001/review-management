package com.reviewservice.businees.objects;

public enum SubscriptionTypes {
	SENTIMENT_ANALYSIS_BASIC, SENTIMENT_ANALYSIS_ADVANCED, REPORT_GENERATION;

	public boolean equalTo(String str) {
		return this.name().equals(str);
	}
}
