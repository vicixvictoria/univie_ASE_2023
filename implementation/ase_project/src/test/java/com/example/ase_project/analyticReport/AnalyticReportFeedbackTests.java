package com.example.ase_project.analyticReport;

import com.example.ase_project.analyticReport.model.data.AnalyticReportFeedback;
import com.example.ase_project.feedback.model.data.Feedback;
import com.example.ase_project.feedback.model.data.FeedbackList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;

public class AnalyticReportFeedbackTests {

    private FeedbackList getFeedbackListWithComments(int numberOfFeedbacks) {
        FeedbackList feedbackList = new FeedbackList();
        for (int i = 1; i < numberOfFeedbacks+1; i++) {
            feedbackList.add(new Feedback("100", Integer.toString(100+i), i%5, i%5, i%5, "comment"));
        }
        return feedbackList;
    }
    private FeedbackList getFeedbackListWithoutComments(int numberOfFeedbacks) {
        FeedbackList feedbackList = new FeedbackList();
        for (int i = 1; i < numberOfFeedbacks+1; i++) {
            feedbackList.add(new Feedback("100", Integer.toString(100+i), i%5, i%5, i%5, null));
        }
        return feedbackList;
    }

    @Test
    public void AnalyticReport_getAnalyticReportAttendee_getNumberOfComments_validCall() {
        FeedbackList feedbackListWith = getFeedbackListWithComments(3);
        FeedbackList feedbackListWithout = getFeedbackListWithoutComments(4);

        FeedbackList totalFeedback = new FeedbackList();
        totalFeedback.add(feedbackListWith);
        totalFeedback.add(feedbackListWithout);

        AnalyticReportFeedback report = new AnalyticReportFeedback("100", totalFeedback);

        Assertions.assertEquals(report.eventID(), "100");
        Assertions.assertEquals(3, report.getNumberOfComments());
    }

}
