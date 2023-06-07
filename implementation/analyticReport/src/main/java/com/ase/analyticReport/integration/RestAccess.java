package com.ase.analyticReport.integration;

import com.ase.analyticReport.business.data.feedback.MyFeedbackList;
import com.ase.common.event.Event;
import com.ase.common.feedback.FeedbackList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

@Component
public class RestAccess {
    private final RestTemplate restTemplate = new RestTemplate();

    public RestAccess() {};
    private static final Converter converter = new Converter();

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private static final String GET_FEEDBACK_ENDPOINT = "${FEEDBACK-ENDPOINT}/api/v1/feedback/event/";
    private static final String GET_EVENT_ENDPOINT = "${EVENT-ENDPOINT}/api/v1/event/";
    private static final String GET_ATTENDING_ENDPOINT = "${ATTENDANCE-ENDPOINT}/api/v1/attendance/";


    public int getNumberOfAttending(String eventID) {
        int attendings = 0;
        try {
            attendings = restTemplate.getForObject(GET_ATTENDING_ENDPOINT + "/" + eventID, int.class);
        }
        catch (ResourceAccessException | HttpClientErrorException | NullPointerException | IllegalArgumentException exception) {
            LOGGER.warn(GET_ATTENDING_ENDPOINT + " endpoint is not available");
        }
        return attendings;
    }

    public int getCapacity(String eventID) {
        int capacity = 0;
        try {
            capacity = converter.networkEventToMyEvent(restTemplate.getForObject(GET_EVENT_ENDPOINT + "/" + eventID, Event.class)).getCapacity();
        }
        catch (ResourceAccessException | HttpClientErrorException | NullPointerException | IllegalArgumentException exception) {
            LOGGER.warn(GET_EVENT_ENDPOINT + " endpoint is not available");
        }
        return capacity;
    }


    public MyFeedbackList getFeedbackList(String eventID) {
        MyFeedbackList feedbackList = null;
        try {
            feedbackList = converter.networkFeedbackListToMyFeedbackList(restTemplate.getForEntity(GET_FEEDBACK_ENDPOINT + "/" + eventID, FeedbackList.class).getBody());
        }
        catch (ResourceAccessException | HttpClientErrorException | NullPointerException | IllegalArgumentException exception) {
            LOGGER.warn(GET_FEEDBACK_ENDPOINT + " endpoint is not available");
            feedbackList = new MyFeedbackList();
        }
        return feedbackList;
    }

}
