package com.example.ase_project.feedback;

import com.example.ase_project.feedback.model.data.Feedback;
import com.example.ase_project.feedback.model.FeedbackService;
import com.example.ase_project.feedback.model.data.FeedbackList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/feedback/")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public FeedbackController (FeedbackService service) {
        this.feedbackService = service;
    }

    @GetMapping(value = "")
    public ResponseEntity<FeedbackList> getAllFeedback() {
        LOGGER.debug("GET api/v1/feedback");
        return feedbackService.getAll();
    }
    @PostMapping(value = "")
    public ResponseEntity<String> createFeedback(@RequestBody Feedback feedback) {
        LOGGER.debug("POST api/v1/feedback");
        return feedbackService.createFeedback(feedback);
    }

    @GetMapping(value = "/{feedbackID}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable String feedbackID) {
        LOGGER.debug("GET api/v1/feedback/{}", feedbackID);
        return feedbackService.getFeedback(feedbackID);
    }
    @GetMapping(value = "event/{eventID}")
    public ResponseEntity<FeedbackList> getEventFeedback(@PathVariable String eventID) {
        LOGGER.debug("GET api/v1/feedback/event/{}", eventID);
        return feedbackService.getEventFeedback(eventID);
    }

    @GetMapping(value = "user/{userID}")
    public ResponseEntity<FeedbackList> getUserFeedback(@PathVariable String userID) {
        LOGGER.debug("GET api/v1/feedback/user/{}", userID);
        return feedbackService.getUserFeedback(userID);
    }
}
