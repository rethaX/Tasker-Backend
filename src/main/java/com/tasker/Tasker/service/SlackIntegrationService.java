package com.tasker.Tasker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SlackIntegrationService {
    private static final Logger logger = LoggerFactory.getLogger(SlackIntegrationService.class);

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    private final RestTemplate restTemplate;

    public SlackIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendSlackNotification(String message) {
        logger.info("Sending Slack notification: {}", message);
        String payload = "{\"text\":\"" + message + "\"}";
        try {
            restTemplate.postForEntity(slackWebhookUrl, payload, String.class);
        } catch (Exception e) {
            logger.error("Failed to send Slack notification: {}", e.getMessage());
        }
    }
}