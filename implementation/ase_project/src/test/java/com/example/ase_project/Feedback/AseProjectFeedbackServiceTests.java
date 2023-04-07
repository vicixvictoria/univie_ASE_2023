package com.example.ase_project.Feedback;

import com.example.ase_project.Feedback.model.FeedbackService;
import com.example.ase_project.Feedback.model.data.FeedbackContent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AseProjectFeedbackServiceTests {
    FeedbackService service = new FeedbackService();

    @BeforeEach
    public void resetFeedbackService(){
        service = new FeedbackService();
    }

    @Test
    public void feedbackService_createFeedback_validCall() {
        FeedbackContent content1 = new FeedbackContent(1, "very nice");

        boolean response1 = service.createFeedback("1", "100", content1);

        Assertions.assertTrue(response1);
    }
    @Test
    public void feedbackService_createFeedback_userIdIsNull() {
        FeedbackContent content1 = new FeedbackContent(1, "very nice");

        boolean response1 = service.createFeedback(null, "100", content1);

        Assertions.assertFalse(response1);
    }
    @Test
    public void feedbackService_createFeedback_eventIdIsNull() {
        FeedbackContent content1 = new FeedbackContent(1, "very nice");

        boolean response1 = service.createFeedback("1", null, content1);

        Assertions.assertFalse(response1);
    }
    @Test
    public void feedbackService_createFeedback_feedbackContentIsNull() {
        boolean response1 = service.createFeedback("1", "100", null);

        Assertions.assertFalse(response1);
    }
    @Test
    public void feedbackService_getFeedbackForEvent_validCall() {
        this.createValidFeedbacksForEvent(5, "100");
        Collection<FeedbackContent> feedbackContents = service.getFeedbackForEvent("100");
        Assertions.assertEquals(5, feedbackContents.size());
    }
    @Test
    public void feedbackService_getFeedbackForEvent_eventIdIsNull() {
        this.createValidFeedbacksForEvent(5, "100");
        Collection<FeedbackContent> feedbackContents = service.getFeedbackForEvent(null);
        Assertions.assertEquals(0, feedbackContents.size());
    }
    @Test
    public void feedbackService_getFeedbackForUser_validCall() {
        this.createValidFeedbacksForUser(5, "1");
        Collection<FeedbackContent> feedbackContents = service.getFeedbackForUser("1");
        Assertions.assertEquals(5, feedbackContents.size());
    }
    @Test
    public void feedbackService_getFeedbackForUser_invalidCall() {
        this.createValidFeedbacksForUser(7, "1");
        Collection<FeedbackContent> feedbackContents = service.getFeedbackForUser(null);
        Assertions.assertEquals(0, feedbackContents.size());
    }
    private void createValidFeedbacksForEvent(int numberOfFeedbacks, String eventID) {
        for (int i = 0; i < numberOfFeedbacks; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 5 + 1);
            FeedbackContent content = new FeedbackContent(randomNum, Integer.toString(i));
            boolean response = service.createFeedback(Integer.toString(i), eventID, content);
            Assertions.assertTrue(response);

        }
    }
    private void createValidFeedbacksForUser(int numberOfFeedbacks, String userID) {
        for (int i = 0; i < numberOfFeedbacks; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 5 + 1);
            FeedbackContent content = new FeedbackContent(randomNum, Integer.toString(i));
            boolean response = service.createFeedback(userID, Integer.toString(i), content);
            Assertions.assertTrue(response);
        }
    }





}
