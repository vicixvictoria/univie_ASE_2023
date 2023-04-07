package com.example.ase_project.Feedback;

import com.example.ase_project.Feedback.model.data.FeedbackContent;
import com.example.ase_project.Feedback.model.repository.FeedbackRepositoryLocal;
import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class AseProjectFeedbackRepositoryLocalTests {
    private FeedbackRepositoryLocal repository = new FeedbackRepositoryLocal();

    @BeforeEach
    public void resetRepository() {
        repository = new FeedbackRepositoryLocal();
    }
    @Test
    public void FeedbackRepositoryLocal_addFeedback_validCall() {
        Collection<FeedbackContent> contentsByEvent = repository.getFeedbackForEvent("1");
        Collection<FeedbackContent> contentsByUser = repository.getFeedbackForUser("1");

        int sizePreAdditionUser = contentsByUser.size();
        int sizePreAdditionEvent = contentsByEvent.size();

        FeedbackContent content = new FeedbackContent(3, "nice event");
        repository.addFeedback("1","1",content);

        int sizePostAdditionEvent = contentsByEvent.size();
        int sizePostAdditionUser = contentsByUser.size();

        // check difference in size before and after addition
        Assertions.assertFalse(sizePreAdditionUser == sizePostAdditionUser);
        Assertions.assertFalse(sizePreAdditionEvent == sizePostAdditionEvent);
    }

    @Test
    public void FeedbackRepositoryLocal_getFeedbackByEvent() {
        // add feedback
        FeedbackContent content1 = new FeedbackContent(1, "very nice");
        FeedbackContent content2 = new FeedbackContent(2, "nice");
        FeedbackContent content3 = new FeedbackContent(3, "ok");
        FeedbackContent content4 = new FeedbackContent(4, "not very nice");
        FeedbackContent content5 = new FeedbackContent(5, "definitely not nice");
        repository.addFeedback("1", "100", content1);
        repository.addFeedback("2", "100", content2);
        repository.addFeedback("3", "100", content3);
        repository.addFeedback("4", "100", content4);
        repository.addFeedback("5", "100", content5);

        // fetch feedbacks for event
        Collection<FeedbackContent> feedbackContents1 = repository.getFeedbackForEvent("100");
        Collection<FeedbackContent> feedbackContents2 = repository.getFeedbackForEvent("200");

        Assertions.assertTrue(feedbackContents1.size() == 5);
        Assertions.assertTrue(feedbackContents2.size() == 0);
    }

    @Test
    public void FeedbackRepositoryLocal_getFeedbackByUser() {
        // add feedback
        FeedbackContent content1 = new FeedbackContent(1, "very nice");
        FeedbackContent content2 = new FeedbackContent(2, "nice");
        FeedbackContent content3 = new FeedbackContent(3, "ok");
        FeedbackContent content4 = new FeedbackContent(4, "not very nice");
        FeedbackContent content5 = new FeedbackContent(5, "definitely not nice");
        repository.addFeedback("1", "101", content1);
        repository.addFeedback("1", "102", content2);
        repository.addFeedback("1", "103", content3);
        repository.addFeedback("1", "104", content4);
        repository.addFeedback("1", "105", content5);

        // fetch feedbacks for event
        Collection<FeedbackContent> feedbackContents1 = repository.getFeedbackForUser("1");
        Collection<FeedbackContent> feedbackContents2 = repository.getFeedbackForUser("2");

        Assertions.assertTrue(feedbackContents1.size() == 5);
        Assertions.assertTrue(feedbackContents2.size() == 0);
    }
}
