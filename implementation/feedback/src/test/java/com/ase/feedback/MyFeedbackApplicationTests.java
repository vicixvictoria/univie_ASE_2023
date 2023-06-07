package com.ase.feedback;

import static org.mockito.Mockito.when;

import com.ase.common.feedback.FeedbackList;
import com.ase.feedback.business.FeedbackService;
import com.ase.feedback.integration.Converter;
import com.ase.feedback.integration.Publisher;
import com.ase.feedback.domain.MyFeedback;
import com.ase.feedback.dataAccess.IFeedbackRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MyFeedbackApplicationTests {

    IFeedbackRepository repository;
    FeedbackService service;

    Converter converter = new Converter();

    @BeforeEach
    public void setRepository() {
        MyFeedback myFeedback1 = new MyFeedback("100", "200", 3, 1, 2, "food was bad");
        MyFeedback myFeedback2 = new MyFeedback("100", "201", 3, 4, 5, "food was medium");
        MyFeedback myFeedback3 = new MyFeedback("101", "200", 3, 1, 2, "food was good");

        ArrayList<MyFeedback> mockResponseEvent = new ArrayList<>();
        ArrayList<MyFeedback> mockResponseUser = new ArrayList<>();
        ArrayList<MyFeedback> mockResponseAll = new ArrayList<>();

        mockResponseEvent.add(myFeedback1);
        mockResponseEvent.add(myFeedback2);
        mockResponseUser.add(myFeedback1);
        mockResponseUser.add(myFeedback3);
        mockResponseAll.add(myFeedback1);
        mockResponseAll.add(myFeedback2);
        mockResponseAll.add(myFeedback3);

        repository = Mockito.mock(IFeedbackRepository.class);

        when(repository.getByEventID("100")).thenReturn(mockResponseEvent);
        when(repository.getByUserID("200")).thenReturn(mockResponseUser);
        when(repository.findAll()).thenReturn(mockResponseAll);

        RabbitTemplate template = Mockito.mock(RabbitTemplate.class);
        Publisher publisher = new Publisher(template);

        service = new FeedbackService(repository, publisher);
    }

    @Test
    public void feedback_createFeedback_validCall() {
        /*
        this test will fail if the feedback creation throws an exception
         */
        MyFeedback myFeedback1 = new MyFeedback("100", "200", 3, 1, 2, "food was bad");
        MyFeedback myFeedback2 = new MyFeedback("100", "201", 3, 4, 5, "food was medium");
        MyFeedback myFeedback3 = new MyFeedback("101", "202", 3, 1, 2, "food was good");
        MyFeedback myFeedback4 = new MyFeedback("101", "202", 3, 1, 2, null);
        Assertions.assertNotNull(myFeedback4);
    }
    @Test
    public void feedback_createFeedback_invalidCall() {
        Executable test1 = () -> new MyFeedback(null, "200", 3, 1, 2, "food was bad");
        Executable test2 = () -> new MyFeedback("100", null, 3, 2, 3, "food was bad");
        Executable test3 = () -> new MyFeedback("100", "200", 7, 1, 2, "food was bad");
        Executable test4 = () -> new MyFeedback("100", "200", 0, 1, 2, "food was bad");
        Executable test5 = () -> new MyFeedback("100", "200", -2, 1, 2, "food was bad");
        Executable test6 = () -> new MyFeedback("100", "200", 2, 7, 2, "food was bad");
        Executable test7 = () -> new MyFeedback("100", "200", 2, 0, 2, "food was bad");
        Executable test8 = () -> new MyFeedback("100", "200", 2, -1, 2, "food was bad");
        Executable test9 = () -> new MyFeedback("100", "200", 2, 1, 7, "food was bad");
        Executable test10 = () -> new MyFeedback("100", "200", 2, 1, 0, "food was bad");
        Executable test11 = () -> new MyFeedback("100", "200", 2, 1, -1, "food was bad");

        Assertions.assertThrows(IllegalArgumentException.class, test1);
        Assertions.assertThrows(IllegalArgumentException.class, test2);
        Assertions.assertThrows(IllegalArgumentException.class, test3);
        Assertions.assertThrows(IllegalArgumentException.class, test4);
        Assertions.assertThrows(IllegalArgumentException.class, test5);
        Assertions.assertThrows(IllegalArgumentException.class, test6);
        Assertions.assertThrows(IllegalArgumentException.class, test7);
        Assertions.assertThrows(IllegalArgumentException.class, test8);
        Assertions.assertThrows(IllegalArgumentException.class, test9);
        Assertions.assertThrows(IllegalArgumentException.class, test10);
        Assertions.assertThrows(IllegalArgumentException.class, test11);
    }
    @Test
    public void feedbackService_storeFeedback_validCall() {
        MyFeedback feedback = new MyFeedback("100", "200", 3, 1, 2, "food was bad");
        ResponseEntity<String> return1 = service.createFeedback(converter.feedbackToNetworkFeedback(feedback));
        Assertions.assertEquals(return1.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void feedbackService_createFeedback_feedbackIsNull() {
        ResponseEntity<String> return1 = service.createFeedback(null);
        Assertions.assertEquals(return1.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals("", return1.getBody());

    }
    @Test
    public void feedbackService_createFeedback_fetchFeedback_validCall() {
        // same as mock feedback
        MyFeedback feedback1 = new MyFeedback("100", "200", 3, 1, 2, "food was bad");
        MyFeedback feedback2 = new MyFeedback("100", "201", 3, 4, 5, "food was medium");
        MyFeedback feedback3 = new MyFeedback("101", "202", 3, 1, 2, "food was good");

        ResponseEntity<String> return1 = service.createFeedback(converter.feedbackToNetworkFeedback(feedback1));
        ResponseEntity<String> return2 = service.createFeedback(converter.feedbackToNetworkFeedback(feedback2));
        ResponseEntity<String> return3 = service.createFeedback(converter.feedbackToNetworkFeedback(feedback3));

        ResponseEntity<FeedbackList> fetch1 = service.getEventFeedback("100");
        ResponseEntity<FeedbackList> fetch2 = service.getUserFeedback("200");
        ResponseEntity<FeedbackList> fetch3 = service.getAll();

        Assertions.assertNotNull(return1.getBody());
        Assertions.assertNotNull(return2.getBody());
        Assertions.assertNotNull(return3.getBody());

        Assertions.assertNotEquals(return1.getBody(), return2.getBody());
        Assertions.assertNotEquals(return1.getBody(), return3.getBody());
        Assertions.assertNotEquals(return3.getBody(), return2.getBody());

        Assertions.assertEquals(2, converter.networkFeedbackListToMyFeedbackList(fetch1.getBody()).getFeedbacks().size());
        Assertions.assertEquals(2, converter.networkFeedbackListToMyFeedbackList(fetch2.getBody()).getFeedbacks().size());
        Assertions.assertEquals(3, converter.networkFeedbackListToMyFeedbackList(fetch3.getBody()).getFeedbacks().size());


    }
}