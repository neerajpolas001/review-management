package com.reviewservice.async.handlers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reviewservice.rest.reposeObjects.SentimentAnalysisResponse;

public class Test {

	public static void main(String[] args) throws URISyntaxException, JsonMappingException, JsonProcessingException {

		RestTemplate restTemplate = new RestTemplate();

		URI uri = new URI("http://localhost:6000/sentiment-analysis/advance");
		Map<String, String> map = new HashMap<>();
		map.put("text", "Hotel was very very good");
		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<Map<String, String>>(map);
		ResponseEntity<SentimentAnalysisResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, SentimentAnalysisResponse.class);
		SentimentAnalysisResponse x = responseEntity.getBody();
		System.out.println(x);
	}

	/*
	 * MultiValueMap map = new MultiValueMap(); map.put("text",
	 * "Hotel was very very good"); HttpEntity<MultiValueMap> httpEntity = new
	 * HttpEntity<>(map);
	 */
}
