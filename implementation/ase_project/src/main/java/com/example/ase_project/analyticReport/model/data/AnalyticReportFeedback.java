package com.example.ase_project.analyticReport.model.data;

import com.example.ase_project.feedback.model.data.Feedback;
import com.example.ase_project.feedback.model.data.FeedbackList;

public record AnalyticReportFeedback(String eventID, FeedbackList feedbacks) {
    public AnalyticReportFeedback {
        if (eventID == null) {
            throw new IllegalArgumentException("eventID can not be null");
        }
        if (feedbacks == null) {
            throw new IllegalArgumentException("feedbacks can not be null");
        }

    }

    /**
     * get the mean of overall Rating
     *
     * @return float mean
     */
    public float getMeanOverallRating() {
        float total = 0;
        for (Feedback feedback : this.feedbacks.getFeedbacks()) {
            total += feedback.getRatingOverall();
        }
        return total / this.feedbacks.getFeedbacks().size();
    }

    /**
     * get the number of feedbacks that contain a comment
     *
     * @return int numberOFComments
     */
    public int getNumberOfComments() {
        int numberOfComments = 0;
        for (Feedback feedback : this.feedbacks.getFeedbacks()) {
            if (!feedback.getComment().equals("")) {
                numberOfComments++;
            }
        }
        return numberOfComments;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(this.eventID);
        ret.append(": ");
        for (Feedback feedback : this.feedbacks.getFeedbacks()) {
            ret.append(feedback.toString());
            ret.append(", ");
        }
        return ret.toString();
    }
}
