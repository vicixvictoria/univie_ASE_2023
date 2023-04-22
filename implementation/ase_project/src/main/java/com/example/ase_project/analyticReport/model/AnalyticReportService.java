package com.example.ase_project.analyticReport.model;

import com.example.ase_project.analyticReport.model.data.AnalyticReportFeedback;
import com.example.ase_project.analyticReport.model.data.AnalyticReportEvent;
import com.example.ase_project.event.Event;
import com.example.ase_project.feedback.model.data.FeedbackList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
@Service public class AnalyticReportService {
    private static final String GET_FEEDBACK_ENDPOINT = "http://localhost:8080/api/v1/feedback/event/";
    private static final String GET_EVENT_ENDPOINT = "http://localhost:8080/api/v1/events/";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public AnalyticReportService() {

    }
    public ResponseEntity<AnalyticReportEvent> getAnalyticReportEvent(String eventID) throws IllegalArgumentException{
        if (eventID == null) {
            LOGGER.warn("getAnalyticReportOrganizer: eventID can not be null");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        RestTemplate restTemplate = new RestTemplate();
        Event event = restTemplate.getForObject(GET_EVENT_ENDPOINT + "/event/" + eventID,
                    Event.class);

        if (event == null) {
            LOGGER.warn("getAnalyticReportOrganizer: no event found for eventID: {}", eventID);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        LOGGER.debug("getAnalyticReportOrganizer: Creating analytic report from eventID {}, event fetched with eventService", eventID);
        return new ResponseEntity<>(new AnalyticReportEvent(eventID, event), HttpStatus.OK);
    }
    public ResponseEntity<AnalyticReportFeedback> getAnalyticReportFeedback(String eventID){
        if (eventID == null) {
            LOGGER.warn("getAnalyticReportAttendee: eventID can not be null");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FeedbackList> feedbackList = restTemplate.getForEntity(GET_FEEDBACK_ENDPOINT + eventID, FeedbackList.class);
        LOGGER.debug("getAnalyticReportAttendee: Creating analytic report from eventID {}, feedbacks fetched with feedbackService", eventID);
        return new ResponseEntity<>(new AnalyticReportFeedback(eventID, feedbackList.getBody()), HttpStatus.OK);
    }


}
