package com.ase.feedback.model;

import com.ase.feedback.model.data.Feedback;
import com.ase.feedback.model.data.FeedbackList;
import com.ase.feedback.model.repository.IFeedbackRepository;
import java.lang.invoke.MethodHandles;

import com.ase.feedback.network.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final static Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final IFeedbackRepository repository;
    private final Publisher publisher;

    @Autowired
    public FeedbackService(IFeedbackRepository feedbackRepository, Publisher publisher) {
        this.repository = feedbackRepository;
        this.publisher = publisher;
    }

    /**
     * saves a feedback in the repository. THere are no checks applied. Validity checks are done at
     * Feedback creation.
     *
     * @param feedback to be saved
     * @return ResponseEntity containing the feedbackID and the status code)
     */
    public ResponseEntity<String> createFeedback(Feedback feedback) {
        if (feedback == null) {
            LOGGER.warn("createFeedback: feedback can not be null");
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
        repository.save(feedback);
        publisher.newFeedbackCreated(feedback);
        return new ResponseEntity<>(feedback.getFeedbackID(), HttpStatus.CREATED);
    }

    /**
     * fetches all events that contain the eventID
     *
     * @param eventID: the ID
     * @return ResponseEntity containing a Collection of found Feedbacks
     */
    public ResponseEntity<FeedbackList> getEventFeedback(String eventID) {
        if (eventID == null) {
            LOGGER.warn("getEventFeedback: eventID can not be null");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        FeedbackList feedbacks = new FeedbackList(repository.getByEventID(eventID));
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    /**
     * fetch all events that contain the userID
     *
     * @param userID the ID
     * @return ResponseEntity containing a Collection of found Feedbacks
     */
    public ResponseEntity<FeedbackList> getUserFeedback(String userID) {
        if (userID == null) {
            LOGGER.warn("getUserFeedback: userID can not be null");
            return new ResponseEntity<>(new FeedbackList(), HttpStatus.BAD_REQUEST);
        }
        FeedbackList feedbacks = new FeedbackList(repository.getByUserID(userID));
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    /**
     * get all feedbacks stored.
     *
     * @return ResponseEntity containing a Collection of all Feedbacks
     */
    public ResponseEntity<FeedbackList> getAll() {
        FeedbackList feedbacks = new FeedbackList(repository.findAll());
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    /**
     * get specific feedback by id
     *
     * @param feedbackID the ID
     * @return ResponseEntity containing a Feedback
     */
    public ResponseEntity<Feedback> getFeedback(String feedbackID) {
        if (feedbackID == null) {
            LOGGER.warn("getFeedback: feedbackID can not be null");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        // here no check of the result is needed because I would return null if it hasn't found anything
        // which happens automatically
        return new ResponseEntity<>(repository.getByFeedbackID(feedbackID), HttpStatus.OK);
    }
}
