package com.example.androidendpointtest.service;

import com.example.androidendpointtest.model.PushNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PushService {

	private final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	private final String FIREBASE_SERVER_KEY = "AAAAVkiEPng:APA91bG5Y-CTQkEnVbeZ44DGDF24WN8dqg4dDDSpxb7JRPOwbk3BIRB_3mu8bbGJ8lLMVq3d0vgoIGER_TLgOxLS5w9R8BOanTPlr_wgF3qbtepQteAB_2CHysO7OX4cfe_5rYlQWp0a";

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public PushService(RestTemplate restTemplate, ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public void sendMessage(PushNotification message){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
		httpHeaders.set("Content-Type", "application/json");
		String payload = "";
		try {
			payload = objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		HttpEntity<String> httpEntity = new HttpEntity<>(payload, httpHeaders);
		restTemplate.postForEntity(FIREBASE_API_URL, httpEntity, String.class);
	}
}
