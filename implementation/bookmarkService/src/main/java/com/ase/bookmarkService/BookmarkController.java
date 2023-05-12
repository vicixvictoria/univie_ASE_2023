package com.ase.bookmarkService;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/bookmark")
public class BookmarkController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final BookmarkService service;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.service = bookmarkService;
    }

    @PostMapping("/add/{eventId}/{attendeeId}")
    public BookmarkEvent bookmarkEvent(@PathVariable String eventId,
            @PathVariable String attendeeId) {
        LOGGER.info("POST api/v1/bookmark/add/{}/{}/", eventId, attendeeId);
        return service.addBookmarkEvent(eventId, attendeeId);
    }

    @DeleteMapping("/unbookmarkEvent/{eventId}/{attendeeId}")
    public void unbookmarkEvent(@PathVariable String eventId, @PathVariable String attendeeId) {
        LOGGER.info("DELETE /unbookmarkEvent/{}/{}", eventId, attendeeId);
        service.deleteBookmarkEvent(eventId, attendeeId);
    }

    @GetMapping("bookmarkedEvents/{attendee}")  //all bookmarked events per attendee
    public List<BookmarkEvent> getBookmarkedEventuser(@PathVariable String attendee) {
        LOGGER.info("GET /bookmarkedEvents/{}", attendee);
        return service.getBookmarkedEventsForUser(attendee);
    }
}
