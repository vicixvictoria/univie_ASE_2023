package com.example.ase_project.Feedback.model;

import com.example.ase_project.Feedback.model.data.Feedback;
import com.example.ase_project.Feedback.model.repository.IFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class FeedbackService {
    private final IFeedbackRepository repository;

    @Autowired
    public FeedbackService(IFeedbackRepository feedbackRepository) {
        repository = feedbackRepository;
    }

    /**
     * saves a feedback in the repository. THere are no checks applied. Validity checks are done at Feedback creation.
     * @param feedback to be saved
     * @return feedback
     */
    public boolean createFeedback(Feedback feedback) {
        if (feedback == null) return false;
        repository.save(feedback);
        return true;
    }

    /**
     * fetches all events that contain the eventID
     * @param eventID: the ID
     * @return List of found Feedbacks
     */
    public Collection<Feedback> getFeedbackForEvent(String eventID) {
        return repository.getByEventID(eventID);
    }

    /**
     * fetch all events that contain the userID
     * @param userID the ID
     * @return List of found Feedbacks
     */
    public Collection<Feedback> getFeedbackForUser(String userID) {
        return repository.getByUserID(userID);
    }

    /**
     * get all feedbacks stored.
     * @return List of all Feedbacks
     */
    public Collection<Feedback> getAll() {
        return repository.findAll();
    }

    /**
     * delete all feedbacks. Do only use for testing.
     */
    public void clearFeedbacks() {
        this.repository.deleteAll();
    }
}