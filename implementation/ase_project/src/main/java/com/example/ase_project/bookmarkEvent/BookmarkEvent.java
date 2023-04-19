package com.example.ase_project.bookmarkEvent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.UUID;

@Entity
public class BookmarkEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Id
    private String BookmarkEventID;
    private String eventID;
    private Boolean isBookmarked; //sets and unsets bookmarked false -> previously bookmarked
    private String attendeeID;

    public BookmarkEvent(String bookmarkEventID, String eventID, Boolean isBookmarked, String attendeeID) {
        LOGGER.debug("BookmarkEvent Entity created bookmarkEventID: {}, eventID: {}, attendeeID: {}", bookmarkEventID, eventID, attendeeID);
        this.BookmarkEventID = UUID.randomUUID().toString().replace("-", "");
        this.eventID = eventID;
        this.isBookmarked = isBookmarked;
        this.attendeeID = attendeeID;
    }

    public BookmarkEvent(String event, String attendee, Boolean setBookmark) {
        LOGGER.debug("BookmarkEvent Entity created with event: {} attendee: {}", event, attendee);
        this.BookmarkEventID = UUID.randomUUID().toString().replace("-", "");
        this.eventID = event;
        this.attendeeID = attendee;
        this.isBookmarked = setBookmark;
    }

    public BookmarkEvent(String event, String attendee) {
        LOGGER.debug("BookmarkEvent Entity created with event: {}, attendee: {}", event, attendee);
        this.BookmarkEventID = UUID.randomUUID().toString().replace("-", "");
        this.eventID = event;
        this.attendeeID = attendee;
        this.isBookmarked = true;
    }

    public BookmarkEvent() {
        LOGGER.debug("BookmarkEvent Entity created with random UUID");
        this.BookmarkEventID = UUID.randomUUID().toString().replace("-", "");
    }


    public String getEvent() {
        return this.eventID;
    }

    public void setEvent(String event) {
        this.eventID = event;
    }

    public Boolean getBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public String getAttendee() {
        return this.attendeeID;
    }

    public void setAttendee(String attendee) {
        this.attendeeID = attendee;
    }
}
