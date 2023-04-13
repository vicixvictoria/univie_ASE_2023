package com.example.ase_project.Feedback;

import com.example.ase_project.Feedback.model.data.Feedback;
import com.example.ase_project.Feedback.model.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController (FeedbackService service) {
        this.feedbackService = service;
    }

    @GetMapping(value = "/feedback/")
    public Collection<Feedback> getAllFeedback() {return feedbackService.getAll();}
    @PostMapping(value = "/feedback/")
    public boolean createFeedback(@RequestBody Feedback feedback) {
        return feedbackService.createFeedback(feedback);
    }

    @GetMapping(value = "/feedback/event/{eventID}")
    public Collection<Feedback> getFeedbacksForEvent(@PathVariable String eventID) {
        return this.feedbackService.getFeedbackForEvent(eventID);
    }

    @GetMapping(value = "/feedback/user/{userID}")
    public Collection<Feedback> getUserFeedback(@PathVariable String userID) {
        return this.feedbackService.getFeedbackForUser(userID);
    }
}
