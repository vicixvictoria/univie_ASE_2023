package com.example.ase_project.analyticReport;

import static org.mockito.Mockito.when;

import com.example.ase_project.analyticReport.model.AnalyticReportService;
import com.example.ase_project.analyticReport.model.data.AnalyticReportEvent;
import com.example.ase_project.analyticReport.model.data.AnalyticReportFeedback;
import com.example.ase_project.event.EEventTypes;
import com.example.ase_project.event.Event;
import com.example.ase_project.event.EventService;
import com.example.ase_project.event.IEventRepository;
import com.example.ase_project.feedback.model.FeedbackService;
import com.example.ase_project.feedback.model.data.Feedback;
import com.example.ase_project.feedback.model.repository.IFeedbackRepository;
import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

public class AnalyticReportServiceTests {

    private FeedbackService feedbackService;
    private EventService eventService;

    private AnalyticReportService apService;

    private IFeedbackRepository feedbackRepository;
    private IEventRepository eventRepository;

    private ArrayList<Event> getEventList() {
        Event event1 = new Event("100", EEventTypes.FOOD, 25, new Date(), "vegan food festival",
                "999", "name1");
        Event event2 = new Event("101", EEventTypes.HEALTH, 25, new Date(),
                "is sport healthier than burgers?", "999", "name1");
        Event event3 = new Event("102", EEventTypes.ENTERTAINMENT, 25, new Date(),
                "'i was not entertained' - how to show them they are wrong", "999", "name1");
        ArrayList<Event> mockResponse = new ArrayList<>();
        mockResponse.add(event1);
        mockResponse.add(event2);
        mockResponse.add(event3);
        return mockResponse;
    }

    private ArrayList<Feedback> getFeedbackList() {
        Feedback feedback1 = new Feedback("100", "200", 4, 1, 5, "overall ok");
        Feedback feedback2 = new Feedback("100", "201", 3, 1, 4, "overall medium");
        Feedback feedback3 = new Feedback("101", "200", 1, 1, 1, "overall bad");
        ArrayList<Feedback> mockResponse = new ArrayList<>();
        mockResponse.add(feedback1);
        mockResponse.add(feedback2);
        mockResponse.add(feedback3);
        return mockResponse;
    }

    @BeforeEach
    public void setupService() {
        eventRepository = Mockito.mock(IEventRepository.class);
        feedbackRepository = Mockito.mock(IFeedbackRepository.class);

        apService = new AnalyticReportService();
    }

    @Test
    public void AnalyticReport_getAnalyticReportOrganizer_validCall() {
        when(eventRepository.findEventByeventID("101")).thenReturn(getEventList().get(1));
        AnalyticReportEvent report = new AnalyticReportEvent("101",
                eventService.getEventsByID("101"));

        Assertions.assertNotNull(report);
        Assertions.assertEquals("101", report.eventID());
    }

    @Test
    public void AnalyticReport_getAnalyticReportOrganizer_eventIdIsNull() {
        when(eventRepository.findEventByeventID("101")).thenReturn(getEventList().get(1));
        Executable test = () -> new AnalyticReportEvent(null, eventService.getEventsByID("101"));
        Assertions.assertThrows(IllegalArgumentException.class, test);
    }

    @Test
    public void AnalyticReport_getAnalyticReportOrganizer_eventIsNull() {
        when(eventRepository.findEventByeventID("101")).thenReturn(getEventList().get(1));
        Executable test = () -> new AnalyticReportEvent("101", null);
        Assertions.assertThrows(IllegalArgumentException.class, test);
    }

    @Test
    public void AnalyticReport_getAnalyticReportAttendee_validCall() {
        ArrayList<Feedback> mockResponse = new ArrayList<>();
        mockResponse.add(getFeedbackList().get(0));
        mockResponse.add(getFeedbackList().get(1));
        when(feedbackRepository.getByEventID("100")).thenReturn(mockResponse);

        ResponseEntity<AnalyticReportFeedback> response = apService.getAnalyticReportFeedback(
                "100");
        AnalyticReportFeedback report = response.getBody();

        Assertions.assertEquals(report.eventID(), "100");
        Assertions.assertEquals(report.feedbacks().getFeedbacks().size(),
                feedbackService.getEventFeedback("100").getBody().getFeedbacks().size());
    }

    @Test
    public void AnalyticReport_getAnalyticReportAttendee_eventIdIsNull() {
        Executable test = () -> apService.getAnalyticReportFeedback(null);
        Assertions.assertThrows(IllegalArgumentException.class, test);
    }
}
