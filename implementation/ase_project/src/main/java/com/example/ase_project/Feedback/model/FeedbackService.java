package com.example.ase_project.Feedback.model;

import com.example.ase_project.Feedback.model.data.FeedbackContent;
import com.example.ase_project.Feedback.model.repository.FeedbackRepositoryLocal;
import com.example.ase_project.Feedback.model.repository.IFeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class FeedbackService {
    private final IFeedbackRepository repository;

    public FeedbackService() {
        repository = new FeedbackRepositoryLocal();
    }

    public boolean createFeedback(String userID, String eventID, FeedbackContent feedbackContent) {

        try {
            repository.addFeedback(userID, eventID, feedbackContent);
        } catch (IllegalArgumentException illegalArg) {
            return false;
        }
        return true;
    }

    public Collection<FeedbackContent> getFeedbackForEvent(String eventID) {
        Collection<FeedbackContent> feedbacks = new HashSet<>();
        try {
            feedbacks = repository.getFeedbackForEvent(eventID);
        }
        catch (IllegalArgumentException e) {
            // TODO: log the exception message here
        }
        return feedbacks;
    }

    public Collection<FeedbackContent> getFeedbackForUser(String userID) {
        Collection<FeedbackContent> feedbacks = new HashSet<>();
        try {
            feedbacks = repository.getFeedbackForUser(userID);
        }
        catch (IllegalArgumentException e) {
            // TODO: log the exception message here
        }
        return feedbacks;
    }
}