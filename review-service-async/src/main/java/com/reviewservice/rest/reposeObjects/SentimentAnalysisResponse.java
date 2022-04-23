package com.reviewservice.rest.reposeObjects;

import java.util.Objects;

public class SentimentAnalysisResponse {

	private String sentiment;

	private double polarity;

	public SentimentAnalysisResponse(String sentiment, double polarity) {
		super();
		this.sentiment = sentiment;
		this.polarity = polarity;
	}

	public SentimentAnalysisResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public double getPolarity() {
		return polarity;
	}

	public void setPolarity(double polarity) {
		this.polarity = polarity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(polarity, sentiment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SentimentAnalysisResponse other = (SentimentAnalysisResponse) obj;
		return Double.doubleToLongBits(polarity) == Double.doubleToLongBits(other.polarity) && Objects.equals(sentiment, other.sentiment);
	}

	@Override
	public String toString() {
		return "SentimentAnalysisResponse [sentiment=" + sentiment + ", polarity=" + polarity + "]";
	}

}
