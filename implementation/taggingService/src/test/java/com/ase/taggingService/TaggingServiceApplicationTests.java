package com.ase.taggingService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ase.common.taggingEvent.ETags;
import com.ase.taggingService.repository.ITaggingRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaggingServiceApplicationTests {

    private final String userId = "userId";
    private final String eventId = "eventId";

    private TaggingService taggingService;

    private TaggingController taggingController;

    @Mock
    private ITaggingRepository repository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taggingService = new TaggingService(repository, new Publisher(rabbitTemplate));
        taggingController = new TaggingController(taggingService);
    }

    @Test
    void testGetEventById() {
        String eventId = "eventId";
        String attendeeId = "attendeeId";
        TaggingEvent taggingEvent = new TaggingEvent(attendeeId, eventId, ETags.EDUCATION);

        when(repository.getTaggingEventByEventIdAndUserId(eventId, attendeeId)).thenReturn(
                taggingEvent);

        TaggingEvent result = taggingService.getEventById(eventId, attendeeId);

        assertEquals(taggingEvent, result);
        verify(repository, times(1)).getTaggingEventByEventIdAndUserId(eventId, attendeeId);
    }

    @Test
    void testAddNewTaggingEvent_ExistingEvent() {
        List<ETags> eTags = new ArrayList<>();
        TaggingEvent existingTaggingEvent = new TaggingEvent(userId, eventId, eTags);

        when(repository.existsByEventIdAndUserId(eventId, userId)).thenReturn(true);
        when(repository.getTaggingEventByEventIdAndUserId(eventId, userId)).thenReturn(
                existingTaggingEvent);

        TaggingEvent result = taggingService.addNewTaggingEvent(userId, eventId, eTags);

        assertNotNull(result);
    }

    @Test
    void testAddNewTaggingEvent_NewEvent() {
        ETags eTags = ETags.SPORT;

        when(repository.existsByEventIdAndUserId(eventId, userId)).thenReturn(false);
        TaggingEvent result = taggingService.addNewTaggingEvent(userId, eventId,
                new ArrayList<ETags>(
                        ETags.EDUCATION.ordinal()));

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(eventId, result.getEventId());
    }

    @Test
    void testRemoveTaggingEvent() {
        taggingService.removeTaggingEvent(userId, eventId);
        verify(repository, times(1)).deleteAllByEventIdAndUserId(eventId, userId);
    }
}
