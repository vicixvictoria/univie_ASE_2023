package com.ase.analyticReport;

import com.ase.analyticReport.model.data.AnalyticReportFeedback;
import com.ase.feedback.Feedback;
import com.ase.feedback.FeedbackList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnalyticReportApplicationTests {

    private FeedbackList getFeedbackListWithComments(int numberOfFeedbacks) {
        FeedbackList feedbackList = new FeedbackList();
        for (int i = 1; i < numberOfFeedbacks + 1; i++) {
            feedbackList.add(
                    new Feedback("100", Integer.toString(100 + i), i % 5, i % 5, i % 5, "comment"));
        }
        return feedbackList;
    }

    private FeedbackList getFeedbackListWithoutComments(int numberOfFeedbacks) {
        FeedbackList feedbackList = new FeedbackList();
        for (int i = 1; i < numberOfFeedbacks + 1; i++) {
            feedbackList.add(
                    new Feedback("100", Integer.toString(100 + i), i % 5, i % 5, i % 5, null));
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
