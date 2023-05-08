package com.ase.feedback;

import static org.mockito.Mockito.when;

import com.ase.feedback.model.FeedbackService;
import com.ase.feedback.model.data.Feedback;
import com.ase.feedback.model.data.FeedbackList;
import com.ase.feedback.model.repository.IFeedbackRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FeedbackApplicationTests {

    IFeedbackRepository repository;
    FeedbackService service;

    @BeforeEach
    public void setRepository() {
        Feedback feedback1 = new Feedback("100", "200", 3, 1, 2, "food was bad");
        Feedback feedback2 = new Feedback("100", "201", 3, 4, 5, "food was medium");
        Feedback feedback3 = new Feedback("101", "200", 3, 1, 2, "food was good");

        ArrayList<Feedback> mockResponseEvent = new ArrayList<>();
        ArrayList<Feedback> mockResponseUser = new ArrayList<>();
        ArrayList<Feedback> mockResponseAll = new ArrayList<>();

        mockResponseEvent.add(feedback1);
        mockResponseEvent.add(feedback2);
        mockResponseUser.add(feedback1);
        mockResponseUser.add(feedback3);
        mockResponseAll.add(feedback1);
        mockResponseAll.add(feedback2);
        mockResponseAll.add(feedback3);

        repository = Mockito.mock(IFeedbackRepository.class);

        when(repository.getByEventID("100")).thenReturn(mockResponseEvent);
        when(repository.getByUserID("200")).thenReturn(mockResponseUser);
        when(repository.findAll()).thenReturn(mockResponseAll);

        service = new FeedbackService(repository);
    }

    @Test
    public void feedbackService_createFeedback_fetchFeedback_validCall() {
        // same as mock feedback
        Feedback feedback1 = new Feedback("100", "200", 3, 1, 2, "food was bad");
        Feedback feedback2 = new Feedback("100", "201", 3, 4, 5, "food was medium");
        Feedback feedback3 = new Feedback("101", "202", 3, 1, 2, "food was good");

        ResponseEntity<String> return1 = service.createFeedback(feedback1);
        ResponseEntity<String> return2 = service.createFeedback(feedback2);
        ResponseEntity<String> return3 = service.createFeedback(feedback3);

        ResponseEntity<FeedbackList> fetch1 = service.getEventFeedback("100");
        ResponseEntity<FeedbackList> fetch2 = service.getUserFeedback("200");
        ResponseEntity<FeedbackList> fetch3 = service.getAll();

        Assertions.assertNotNull(return1.getBody());
        Assertions.assertNotNull(return2.getBody());
        Assertions.assertNotNull(return3.getBody());

        Assertions.assertNotEquals(return1.getBody(), return2.getBody());
        Assertions.assertNotEquals(return1.getBody(), return3.getBody());
        Assertions.assertNotEquals(return3.getBody(), return2.getBody());

        Assertions.assertEquals(2, fetch1.getBody().getFeedbacks().size());
        Assertions.assertEquals(2, fetch2.getBody().getFeedbacks().size());
        Assertions.assertEquals(3, fetch3.getBody().getFeedbacks().size());
    }

    @Test
    public void feedbackService_createNewFeedback_argumentsAreNull() {
        Executable test1 = () -> new Feedback(null, "200", 3, 1, 2, "food was bad");
        Executable test2 = () -> new Feedback("100", null, 3, 2, 3, "food was bad");
        Executable test3 = () -> new Feedback("100", "200", 7, 8, 6, "food was bad");
        Executable test4 = () -> new Feedback("100", "200", 3, 1, 2, null);

        Assertions.assertThrows(IllegalArgumentException.class, test1);
        Assertions.assertThrows(IllegalArgumentException.class, test2);
        Assertions.assertThrows(IllegalArgumentException.class, test3);
        Assertions.assertNotNull(test4);
    }

    @Test
    public void feedbackService_createFeedback_feedbackIsNull() {
        ResponseEntity<String> response = service.createFeedback(null);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("", response.getBody());
    }
}