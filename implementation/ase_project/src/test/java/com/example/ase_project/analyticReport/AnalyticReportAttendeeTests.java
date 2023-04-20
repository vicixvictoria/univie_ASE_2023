package com.example.ase_project.analyticReport;

import com.example.ase_project.analyticReport.model.data.AnalyticReportAttendee;
import com.example.ase_project.feedback.model.data.Feedback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class AnalyticReportAttendeeTests {

    private List<Feedback> getFeedbackListWithComments(int numberOfFeedbacks) {
        List<Feedback> feedbackList = new ArrayList<>();
        for (int i = 1; i < numberOfFeedbacks+1; i++) {
            feedbackList.add(new Feedback("100", Integer.toString(100+i), i%5, i%5, i%5, "comment"));
        }
        return feedbackList;
    }
    private List<Feedback> getFeedbackListWithoutComments(int numberOfFeedbacks) {
        List<Feedback> feedbackList = new ArrayList<>();
        for (int i = 1; i < numberOfFeedbacks+1; i++) {
            feedbackList.add(new Feedback("100", Integer.toString(100+i), i%5, i%5, i%5, null));
        }
        return feedbackList;
    }

    @Test
    public void AnalyticReport_getAnalyticReportAttendee_getNumberOfComments_validCall() {
        List<Feedback> feedbackListWith = getFeedbackListWithComments(3);
        List<Feedback> feedbackListWithout = getFeedbackListWithoutComments(4);

        List<Feedback> totalFeedback = new ArrayList<>();
        totalFeedback.addAll(feedbackListWith);
        totalFeedback.addAll(feedbackListWithout);

        AnalyticReportAttendee report = new AnalyticReportAttendee("100", totalFeedback);

        Assertions.assertEquals(report.eventID(), "100");
        Assertions.assertEquals(3, report.getNumberOfComments());
    }

}
