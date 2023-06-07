package com.ase.feedback.business;

import com.ase.common.feedback.Feedback;
import com.ase.common.feedback.FeedbackList;
import com.ase.feedback.domain.MyFeedback;
import com.ase.feedback.domain.MyFeedbackList;
import com.ase.feedback.dataAccess.IFeedbackRepository;
import com.ase.feedback.integration.Converter;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

import com.ase.feedback.integration.Publisher;
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
    private final Converter converter = new Converter();

    @Autowired
    public FeedbackService(IFeedbackRepository feedbackRepository, Publisher publisher) {
        this.repository = feedbackRepository;
        this.publisher = publisher;
    }

    /**
     * saves a myFeedback in the repository. There are no checks applied. Validity checks are done at
     * MyFeedback creation.
     *
     * @param feedback to be saved
     * @return ResponseEntity containing the feedbackID and the status code
     */
    public ResponseEntity<String> createFeedback(Feedback feedback) {
        if (feedback == null) {
            LOGGER.warn("createFeedback: myFeedback can not be null");
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
        MyFeedback myFeedback = converter.networkFeedbackToMyFeedback(feedback);
        repository.save(myFeedback);
        publisher.newFeedbackCreated(myFeedback);
        return new ResponseEntity<>(myFeedback.getFeedbackID(), HttpStatus.CREATED);
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
        MyFeedbackList feedbacks = new MyFeedbackList(repository.getByEventID(eventID));
        return new ResponseEntity<>(converter.feedbackListToNetworkFeedbackList(feedbacks), HttpStatus.OK);
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
            return new ResponseEntity<>(converter.feedbackListToNetworkFeedbackList(new MyFeedbackList()), HttpStatus.BAD_REQUEST);
        }
        MyFeedbackList feedbacks = new MyFeedbackList(repository.getByUserID(userID));
        return new ResponseEntity<>(converter.feedbackListToNetworkFeedbackList(feedbacks), HttpStatus.OK);
    }

    /**
     * get all feedbacks stored.
     *
     * @return ResponseEntity containing a Collection of all Feedbacks
     */
    public ResponseEntity<FeedbackList> getAll() {
        MyFeedbackList feedbacks = new MyFeedbackList(repository.findAll());
        return new ResponseEntity<>(converter.feedbackListToNetworkFeedbackList(feedbacks), HttpStatus.OK);
    }

    /**
     * get specific feedback by id
     *
     * @param feedbackID the ID
     * @return ResponseEntity containing a MyFeedback
     */
    public ResponseEntity<Feedback> getFeedback(String feedbackID) {
        if (feedbackID == null) {
            LOGGER.warn("getFeedback: feedbackID can not be null");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        MyFeedback feedback = null;
        ResponseEntity<Feedback> returnEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        Optional<MyFeedback> queryResult = repository.findById(feedbackID);

        if (queryResult.isPresent()) {
            feedback = queryResult.get();
            returnEntity = new ResponseEntity<>(converter.feedbackToNetworkFeedback(feedback), HttpStatus.OK);
        }
        return returnEntity;
    }
}
