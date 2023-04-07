package com.example.ase_project.Feedback;

import com.example.ase_project.Feedback.model.data.FeedbackContent;
import com.example.ase_project.Feedback.model.FeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**
     *
     * @param feedbackService
     */
    public FeedbackController (FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping(value = "/getFeedbacks/{eventID}")
    public String getFeedback(@PathVariable int eventID){
        return "not implemented yet";
    }

    @GetMapping(value = "/getFeedback/{eventID}/{feedbackID}")
    public String getFeedback(@PathVariable int eventID, @PathVariable int feedbackID){
        return "not implemented yet";
    }

    @PostMapping(value = "/createFeedback/{eventID}/{userID}")
    public boolean createFeedback(@PathVariable String userID, @PathVariable String eventID, @RequestBody FeedbackContent feedbackContent) {
        boolean success = false;
        try {
            success = feedbackService.createFeedback(userID, eventID, feedbackContent);
        }
        catch (IllegalArgumentException illegalArg) {
            return false;
        }
        return success;
    }

    @GetMapping(value = "/getFeedback/{eventID}")
    public Collection<FeedbackContent> getFeedbacksForEvent(@PathVariable String eventID) {
        return this.feedbackService.getFeedbackForEvent(eventID);
    }

    @GetMapping(value = "/getUserFeedback/{userID}")
    public Collection<FeedbackContent> getUserFeedback(@PathVariable String userID) {
        return this.feedbackService.getFeedbackForUser(userID);
    }

}
