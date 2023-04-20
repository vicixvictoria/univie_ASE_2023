package com.example.ase_project.analyticReport.model;

import com.example.ase_project.analyticReport.model.data.AnalyticReportAttendee;
import com.example.ase_project.analyticReport.model.data.AnalyticReportOrganizer;
import com.example.ase_project.event.Event;
import com.example.ase_project.event.EventService;
import com.example.ase_project.feedback.model.FeedbackService;
import com.example.ase_project.feedback.model.data.Feedback;
import com.example.ase_project.sendnotification.NotificationContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;

@Service public class AnalyticReportService {
    private static final String GET_FEEDBACK_ENDPONT = "http://localhost:8080/api/v1/feedback/event/";
    private static final String GET_EVENT_ENDPONT = "http://localhost:8080/api/v1/events/";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public AnalyticReportService() {

    }
    public ResponseEntity<AnalyticReportOrganizer> getAnalyticReportOrganizer(String eventID) throws IllegalArgumentException{
        if (eventID == null) {
            LOGGER.warn("getAnalyticReportOrganizer: eventID can not be null");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        RestTemplate restTemplate = new RestTemplate();
        Event event = restTemplate.getForObject(GET_EVENT_ENDPONT,
                Event.class);

        if (event == null) {
            LOGGER.warn("getAnalyticReportOrganizer: no event found for eventID: {}", eventID);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        // TODO: add the data about attendings and bookmarks here (either they will be added to class event or I have to fetch them from elsewhere
        LOGGER.debug("getAnalyticReportOrganizer: Creating analytic report from eventID {}, event fetched with eventService", eventID);
        return new ResponseEntity<>(new AnalyticReportOrganizer(eventID, event), HttpStatus.OK);
    }
    public ResponseEntity<AnalyticReportAttendee> getAnalyticReportAttendee(String eventID){
        if (eventID == null) {
            LOGGER.warn("getAnalyticReportAttendee: eventID can not be null");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<Feedback> feedbacks = restTemplate.getForObject(GET_FEEDBACK_ENDPONT,
                ArrayList.class);

        if (feedbacks == null) {
            LOGGER.warn("getAnalyticReportAttendee: no event found for eventID: {}", eventID);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        LOGGER.debug("getAnalyticReportAttendee: Creating analytic report from eventID {}, feedbacks fetched with feedbackService", eventID);
        return new ResponseEntity<>(new AnalyticReportAttendee(eventID, feedbacks), HttpStatus.OK);
    }


}
