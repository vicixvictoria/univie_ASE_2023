package com.ase.recommender;

import com.ase.common.event.EEventType;
import com.ase.recommender.business.RecommenderService;
import com.ase.recommender.domain.EventType;
import com.ase.recommender.domain.UserInterest;
import com.ase.recommender.integration.Publisher;
import com.ase.recommender.dataAccess.IEventTypeRepository;
import com.ase.recommender.dataAccess.IRecommenderRepository;
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
        service.recommend("event1", EEventType.HEALTH);
        verify(publisher).recommend(eq("event1"), any());
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
}
