package com.ase.analyticReport.business;

import com.ase.analyticReport.business.data.AnalyticReportEvent;
import com.ase.analyticReport.business.data.AnalyticReportFeedback;
import com.ase.analyticReport.business.data.feedback.MyFeedbackList;
import com.ase.analyticReport.integration.RestAccess;

import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnalyticReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final RestAccess restAccess;

    public AnalyticReportService(RestAccess restAccess) {
        this.restAccess = restAccess;
    }

    public ResponseEntity<AnalyticReportEvent> getAnalyticReportEvent(String eventID) {
        if (eventID == null || eventID.equals("")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        int attendings = restAccess.getNumberOfAttending(eventID);
        int capacity = restAccess.getCapacity(eventID);
        AnalyticReportEvent report = new AnalyticReportEvent(eventID, capacity, attendings);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    public ResponseEntity<AnalyticReportFeedback> getAnalyticReportFeedback(String eventID) {
        if (eventID == null || eventID.equals("")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        MyFeedbackList feedbackList = restAccess.getFeedbackList(eventID);

        AnalyticReportFeedback report = new AnalyticReportFeedback(eventID, feedbackList.size(), feedbackList.getMeanRatingOverall(), feedbackList.getMeanFoodRating(), feedbackList.getMeanLocationRating());
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

}
