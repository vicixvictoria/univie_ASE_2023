package com.ase.bookmarkService;

import com.ase.bookmarkService.repository.IBookmarkRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final IBookmarkRepository repository;

    private final Publisher publisher;


    @Autowired
    public BookmarkService(IBookmarkRepository repository, Publisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    /**
     * method is called to add a bookmarked event, returns the created BookmarkEvent
     *
     * @param eventId, attendeeId as strings
     * @return BookmarkEvent
     */
    public BookmarkEvent addBookmarkEvent(String eventId, String attendeeId) throws Exception {
        LOGGER.debug("Adding new Bookmark Event in Database");
        BookmarkEvent bookmarkEvent = new BookmarkEvent(eventId, attendeeId);
        try {
            repository.save(bookmarkEvent);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Failed to add new BookmarkEvent", e.getMessage());
            throw new Exception("Failed to add new BookmarkEvent", e);
        } catch (Exception e){
            LOGGER.error("Exception occurred while saving new BookmarkEvent", e.getMessage());
            throw new Exception("Failed to add new BookmarkEvent", e);
        }
        try {
            publisher.newBookmarkEvent(eventId, attendeeId);
        } catch (Exception e){
            LOGGER.error("Failed to connect with publisher");
            return bookmarkEvent;
        }
        return bookmarkEvent;
    }

    /**
     * method is called to delete a bookmarked event in case a user unbookmarks the entry
     *
     * @param eventId, attendeeId
     * @return void
     */
    @Transactional
    public void deleteBookmarkEvent(String eventId, String attendeeId) throws Exception {
        LOGGER.debug("Delete Bookmark Event");
        try {
            repository.deleteBookmarkEventsByAttendeeIDAndEventID(attendeeId, eventId);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Entity BookmarkEvent not found", e.getMessage());
            throw new Exception("Failed to delete BookmarkEvent", e);
        } catch (Exception e){
            LOGGER.error("Exception occurred while deleting BookmarkEvent", e.getMessage());
            throw new Exception("Failed to delete BookmarkEvent", e);
        }
        try {
            publisher.deleteBookmarkEvent(eventId, attendeeId);
        } catch (Exception e){
            LOGGER.error("Failed to connect with publisher");
        }
    }

    /**
     * method is called to request all bookmarked events per userId
     *
     * @param attendeeId
     * @return List
     */
    public List<BookmarkEvent> getBookmarkedEventsForUser(String attendeeId) throws Exception {
        LOGGER.debug("get BookmarkedEvent for User");
        List<BookmarkEvent> eventList = new ArrayList<>();
        try {
            eventList = repository.getAllByAttendeeID(attendeeId);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Entity BookmarkEvent not found", e.getMessage());
            throw new Exception("Failed to delete BookmarkEvent", e);
        }
        return eventList;
    }
}
