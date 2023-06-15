package com.ase.analyticReport.integration;

import com.ase.analyticReport.business.data.feedback.MyFeedbackList;
import com.ase.common.event.Event;
import com.ase.common.feedback.FeedbackList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

@Component
public class RestAccess {
    @Value("${attendance.endpoint}")
    private String ATTENDANCE_ENDPOINT;
    @Value("${event.endpoint}")
    private String EVENT_ENDPOINT;
    @Value("${feedback.endpoint}")
    private String FEEDBACK_ENDPOINT;

    private final RestTemplate restTemplate = new RestTemplate();

    public RestAccess() {};
    private static final Converter converter = new Converter();

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    public int getNumberOfAttending(String eventID) {
        int attendings = 0;
        String url = ATTENDANCE_ENDPOINT + "/" + eventID;
        try {
            attendings = restTemplate.getForObject(url, int.class);
        }
        catch (ResourceAccessException | HttpClientErrorException | NullPointerException | IllegalArgumentException exception) {
            LOGGER.warn(url + " endpoint is not available");
            LOGGER.warn(exception.getMessage());
        }
        return attendings;
    }

    public int getCapacity(String eventID) {
        int capacity = 0;
        String url = EVENT_ENDPOINT + "/event/" + eventID;
        try {
            capacity = converter.networkEventToMyEvent(restTemplate.getForObject(url, Event.class)).getCapacity();
        }
        catch (ResourceAccessException | HttpClientErrorException | NullPointerException | IllegalArgumentException exception) {
            LOGGER.warn(url + " endpoint is not available");
            LOGGER.warn(exception.getMessage());
        }
        return capacity;
    }


    public MyFeedbackList getFeedbackList(String eventID) {
        MyFeedbackList feedbackList = null;
        String url = FEEDBACK_ENDPOINT + "/event/" + eventID;
        try {
            feedbackList = converter.networkFeedbackListToMyFeedbackList(restTemplate.getForEntity(url, FeedbackList.class).getBody());
        }
        catch (ResourceAccessException | HttpClientErrorException | NullPointerException | IllegalArgumentException exception) {
            LOGGER.warn(url + " endpoint is not available");
            LOGGER.warn(exception.getMessage());
            feedbackList = new MyFeedbackList();
        }
        return feedbackList;
    }

}
