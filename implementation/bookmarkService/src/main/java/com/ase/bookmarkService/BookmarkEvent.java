package com.ase.bookmarkService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.invoke.MethodHandles;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class BookmarkEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private String BookmarkEventID;
    private String eventID;
    private String attendeeID;

    public BookmarkEvent(String bookmarkEventID, String eventID, String attendeeID) {
        LOGGER.debug(
                "BookmarkEvent Entity created bookmarkEventID: {}, eventID: {}, attendeeID: {}",
                bookmarkEventID, eventID, attendeeID);
        this.BookmarkEventID = UUID.randomUUID().toString().replace("-", "");
        this.eventID = eventID;
        this.attendeeID = attendeeID;
    }

    public BookmarkEvent(String event, String attendee, Boolean setBookmark) {
        LOGGER.debug("BookmarkEvent Entity created with event: {} attendee: {}", event, attendee);
        this.BookmarkEventID = UUID.randomUUID().toString().replace("-", "");
        this.eventID = event;
        this.attendeeID = attendee;
    }

    public BookmarkEvent(String event, String attendee) {
        LOGGER.debug("BookmarkEvent Entity created with event: {}, attendee: {}", event, attendee);
        this.BookmarkEventID = UUID.randomUUID().toString().replace("-", "");
        this.eventID = event;
        this.attendeeID = attendee;
    }


    public BookmarkEvent() {
        LOGGER.debug("BookmarkEvent Entity created with random UUID");
    }

    public String getEvent() {
        return this.eventID;
    }

    public void setEvent(String event) {
        this.eventID = event;
    }

    public String getAttendee() {
        return this.attendeeID;
    }

    public void setAttendee(String attendee) {
        this.attendeeID = attendee;
    }
}
