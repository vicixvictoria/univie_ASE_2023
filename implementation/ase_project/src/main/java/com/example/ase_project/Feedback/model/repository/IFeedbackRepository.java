package com.example.ase_project.Feedback.model.repository;

import com.example.ase_project.Feedback.model.data.FeedbackContent;

import java.util.Collection;

public interface IFeedbackRepository {
    Collection<FeedbackContent> getFeedbackForUser(String userID);
    Collection<FeedbackContent> getFeedbackForEvent(String eventID);
    void addFeedback(String userID, String eventID, FeedbackContent feedbackContent);
}
