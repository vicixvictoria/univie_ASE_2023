package com.example.ase_project.Feedback;

import com.example.ase_project.Feedback.model.FeedbackService;
import com.example.ase_project.Feedback.model.data.Feedback;
import com.example.ase_project.Feedback.model.repository.IFeedbackRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AseProjectFeedbackServiceTests {
    IFeedbackRepository repository;
    FeedbackService service;

    @BeforeEach
    public void setRepository() {
        Feedback mockFeedback1 = Mockito.mock(Feedback.class);
        Feedback mockFeedback2 = Mockito.mock(Feedback.class);
        Feedback mockFeedback3 = Mockito.mock(Feedback.class);
        ArrayList<Feedback> mockResponse = new ArrayList<>();

        mockResponse.add(mockFeedback1);
        mockResponse.add(mockFeedback2);
        mockResponse.add(mockFeedback3);

        repository = Mockito.mock(IFeedbackRepository.class);
        when(repository.save(any(Feedback.class))).then(returnsFirstArg());
        when(repository.getByEventID(any(String.class))).thenReturn(mockResponse);
        when(repository.getByUserID(any(String.class))).thenReturn(mockResponse);

        service = new FeedbackService(repository);
    }

    @Test
    public void feedbackService_createFeedback_fetchFeedback_validCall() {
        Feedback feedback1 = new Feedback("100", "200", 3, 1, 2, "food was bad");
        Feedback feedback2 = new Feedback("100", "201", 3, 4, 5, "food was medium");
        Feedback feedback3 = new Feedback("101", "202", 3, 1, 2, "food was good");

        boolean return1 = service.createFeedback(feedback1);
        boolean return2 = service.createFeedback(feedback2);
        boolean return3 = service.createFeedback(feedback3);

        Collection<Feedback> fetch1 = service.getFeedbackForEvent("100");
        Collection<Feedback> fetch2 = service.getFeedbackForUser("200");
        Collection<Feedback> fetch3 = service.getAll();

        Assertions.assertEquals(3, fetch1.size());
        Assertions.assertEquals(3, fetch2.size());
    }

    @Test
    public void feedbackService_createNewFeedback_invalidCall() {
        Executable test1 = () -> new Feedback(null, "200", 3, 1,2,"food was bad");
        Executable test2 = () -> new Feedback("100", null, 3,2,3, "food was bad");
        Executable test3 = () -> new Feedback("100", "200", 7, 8,6,"food was bad");
        Executable test4 = () -> new Feedback("100", "200", 3,1,2, null);

        Assertions.assertThrows(IllegalArgumentException.class, test1);
        Assertions.assertThrows(IllegalArgumentException.class, test2);
        Assertions.assertThrows(IllegalArgumentException.class, test3);
        Assertions.assertNotNull(test4);
    }

    @Test
    public void feedbackService_createFeedback_feedbackIsNull() {
        Feedback feedback = null;
        boolean return1 = service.createFeedback(feedback);

        Assertions.assertFalse(return1);
    }
}
