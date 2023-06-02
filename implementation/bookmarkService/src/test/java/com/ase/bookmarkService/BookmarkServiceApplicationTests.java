package com.ase.bookmarkService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ase.bookmarkService.repository.IBookmarkRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookmarkServiceApplicationTests {

    private BookmarkService bookmarkService;
    private BookmarkController bookmarkController;

    private final String eventId = "eventId";
    private final String attendeeId = "attendeeId";
    @Mock
    private IBookmarkRepository repository;

    @Mock
    private Publisher publisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookmarkService = new BookmarkService(repository, publisher);
        bookmarkController = new BookmarkController(bookmarkService);
    }

    @Test
    void testAddBookmarkEvent() {
        BookmarkEvent bookmarkEvent = new BookmarkEvent(eventId, attendeeId);
        when(repository.save(bookmarkEvent)).thenReturn(bookmarkEvent);
        BookmarkEvent result = bookmarkService.addBookmarkEvent(eventId, attendeeId);
        assertEquals(bookmarkEvent.getEvent(), result.getEvent());
    }

    @Test
    void testAddBookmarkEvent_EntityNotFoundException() {
        when(repository.save(any())).thenThrow(EntityNotFoundException.class);
        BookmarkEvent result = bookmarkService.addBookmarkEvent(eventId, attendeeId);
        assertNull(result);
        verify(repository, times(1)).save(any());
    }

    @Test
    @Transactional
    void testDeleteBookmarkEvent() {
        bookmarkService.deleteBookmarkEvent(eventId, attendeeId);
        verify(repository, times(1)).deleteBookmarkEventsByAttendeeIDAndEventID(attendeeId,
                eventId);
        verify(publisher, times(1)).deleteBookmarkEvent(eventId, attendeeId);
    }

    @Test
    void testDeleteBookmarkEvent_EntityNotFoundException() {
        doThrow(EntityNotFoundException.class).when(repository)
                .deleteBookmarkEventsByAttendeeIDAndEventID(attendeeId, eventId);
        assertDoesNotThrow(() -> bookmarkService.deleteBookmarkEvent(eventId, attendeeId));
        verify(repository, times(1)).deleteBookmarkEventsByAttendeeIDAndEventID(attendeeId,
                eventId);
        verify(publisher, times(0)).deleteBookmarkEvent(eventId, attendeeId);
    }

    @Test
    void testGetBookmarkedEventsForUser() {
        List<BookmarkEvent> eventList = new ArrayList<>();
        when(repository.getAllByAttendeeID(attendeeId)).thenReturn(eventList);
        List<BookmarkEvent> result = bookmarkService.getBookmarkedEventsForUser(attendeeId);
        assertEquals(eventList, result);
        verify(repository, times(1)).getAllByAttendeeID(attendeeId);
    }

    @Test
    void testGetBookmarkedEventsForUser_EntityNotFoundException() {
        when(repository.getAllByAttendeeID(attendeeId)).thenThrow(EntityNotFoundException.class);
        List<BookmarkEvent> result = bookmarkService.getBookmarkedEventsForUser(attendeeId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).getAllByAttendeeID(attendeeId);
    }

    @Test
    void testUnbookmarkEventController() {
        bookmarkController.unbookmarkEvent(eventId, attendeeId);
        List<BookmarkEvent>events = bookmarkService.getBookmarkedEventsForUser(attendeeId);
        assertEquals(events.size(), 0);
    }

}
