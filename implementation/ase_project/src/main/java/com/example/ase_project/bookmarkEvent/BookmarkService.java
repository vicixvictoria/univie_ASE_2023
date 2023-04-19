package com.example.ase_project.bookmarkEvent;

import com.example.ase_project.bookmarkEvent.repository.IBookmarkRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final IBookmarkRepository repository;

    @Autowired
    public BookmarkService(IBookmarkRepository repository) {
        this.repository = repository;
    }

    /**
     * method is called to add a bookmarked event, returns the created BookmarkEvent
     *
     * @param  eventId, attendeeId
     * @return BookmarkEvent
     */
    public BookmarkEvent addBookmarkEvent(String eventId, String attendeeId) {
        LOGGER.debug("Adding new Bookmark Event in Database");
        BookmarkEvent bookmarkEvent = new BookmarkEvent(eventId, attendeeId);
        try {
            repository.save(bookmarkEvent);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Failed to add new BookmarkEvent", e.getMessage());
            return null;
        }
        return bookmarkEvent;
    }

    /**
     * method is called to delete a bookmarked event in case a user unbookmarks the entry
     *
     * @param  eventId, attendeeId
     * @return void
     */
    @Transactional
    public void deleteBookmarkEvent(String eventId, String attendeeId) {
        LOGGER.debug("Delete Bookmark Event");
        try {
            repository.deleteBookmarkEventsByAttendeeIDAndEventID(attendeeId, eventId);
        } catch (EntityNotFoundException e){
            LOGGER.error("Entity BookmarkEvent not found", e.getMessage());
        }
    }

    /**
     * method is called to request all bookmarked events per userId
     *
     * @param attendeeId
     * @return List
     */
    public List<BookmarkEvent> getBookmarkedEventsForUser(String attendeeId) {
        LOGGER.debug("get BookmarkedEvent for User");
        List<BookmarkEvent> eventList = new ArrayList<>();
        try {
            eventList =  repository.getAllByAttendeeID(attendeeId);
            return eventList;
        }catch (EntityNotFoundException e){
            LOGGER.error("Entity BookmarkEvent not found", e.getMessage());
        }
        LOGGER.warn("no BookmarkedEvents found by attendeeId");
        return eventList;
    }
}
