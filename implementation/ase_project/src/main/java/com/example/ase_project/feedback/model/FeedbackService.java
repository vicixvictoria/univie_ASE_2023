package com.example.ase_project.feedback.model;

import com.example.ase_project.feedback.model.data.Feedback;
import com.example.ase_project.feedback.model.repository.IFeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class FeedbackService {
    private final IFeedbackRepository repository;
    private final static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public FeedbackService(IFeedbackRepository feedbackRepository) {
        repository = feedbackRepository;
    }

    /**
     * saves a feedback in the repository. THere are no checks applied. Validity checks are done at Feedback creation.
     * @param feedback to be saved
     * @return ResponseEntity containing the feedbackID and the status code)
     */
    public ResponseEntity<String> createFeedback(Feedback feedback) {
        if (feedback == null) {
            LOGGER.warn("createFeedback: feedback can not be null");
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
        repository.save(feedback);
        return new ResponseEntity<>(feedback.getFeedbackID(), HttpStatus.CREATED);
    }

    /**
     * fetches all events that contain the eventID
     * @param eventID: the ID
     * @return ResponseEntity containing a Collection of found Feedbacks
     */
    public ResponseEntity<Collection<Feedback>> getEventFeedback(String eventID) {
        if (eventID == null) {
            LOGGER.warn("getEventFeedback: eventID can not be null");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Collection<Feedback> feedbacks = repository.getByEventID(eventID);
        if (feedbacks == null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    /**
     * fetch all events that contain the userID
     * @param userID the ID
     * @return ResponseEntity containing a Collection of found Feedbacks
     */
    public ResponseEntity<Collection<Feedback>> getUserFeedback(String userID) {
        if (userID == null) {
            LOGGER.warn("getUserFeedback: userID can not be null");
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
        Collection<Feedback> feedbacks = repository.getByUserID(userID);
        if (feedbacks == null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    /**
     * get all feedbacks stored.
     * @return ResponseEntity containing a Collection of all Feedbacks
     */
    public ResponseEntity<Collection<Feedback>> getAll() {
        Collection<Feedback> feedbacks = repository.findAll();
        if (feedbacks == null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    /**
     * get specific feedback by id
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