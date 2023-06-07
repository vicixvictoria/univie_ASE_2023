package com.ase.recommender;

import com.ase.common.event.EEventType;
import com.ase.recommender.data.EventType;
import com.ase.recommender.data.UserInterest;
import com.ase.recommender.network.Publisher;
import com.ase.recommender.repository.IEventTypeRepository;
import com.ase.recommender.repository.IRecommenderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The RecommenderServiceTest class contains unit tests for RecommenderService.
 */
public class RecommenderServiceTest {

    @InjectMocks
    private RecommenderService service;

    @Mock
    private IRecommenderRepository recommenderRepository;

    @Mock
    private IEventTypeRepository eventTypeRepository;

    @Mock
    private Publisher publisher;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the recommend method of RecommenderService.
     */
    @Test
    public void testRecommend() {
        List<UserInterest> interestList = Arrays.asList(
                new UserInterest("user1"), new UserInterest("user2"));
        when(recommenderRepository.findAll()).thenReturn(interestList);
        service.recommend(EEventType.HEALTH);
        verify(publisher).recommend(any());
    }

    /**
     * Tests the addEventType method of RecommenderService.
     */
    @Test
    public void testAddEventType() {
        service.addEventType("event1", EEventType.HEALTH);
        verify(eventTypeRepository).save(any(EventType.class));
    }

    /**
     * Tests the updateEventType method of RecommenderService.
     */
    @Test
    public void testUpdateEventType() {
        EventType eventType = new EventType("event1", EEventType.FOOD);
        when(eventTypeRepository.getByEventID("event1")).thenReturn(eventType);
        service.updateEventType("event1", EEventType.ENTERTAINMENT);
        assertEquals(EEventType.ENTERTAINMENT, eventType.getEventType());
    }

    /**
     * Tests the addInterest method of RecommenderService.
     */
    @Test
    public void testAddInterest() {
        when(eventTypeRepository.existsById("event1")).thenReturn(true);
        when(recommenderRepository.existsById("user1")).thenReturn(false);
        assertFalse(service.addInterest("event1", "user1"));
    }

    /**
     * Tests the removeInterest method of RecommenderService.
     */
    @Test
    public void testRemoveInterest() {
        when(eventTypeRepository.existsById("event1")).thenReturn(true);
        when(recommenderRepository.existsById("user1")).thenReturn(true);
        UserInterest userInterest = new UserInterest("user1");
        when(recommenderRepository.getByUserID("user1")).thenReturn(userInterest);
        assertFalse(service.removeInterest("event1", "user1"));
    }
}
