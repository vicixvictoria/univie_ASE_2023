package com.ase.taggingService;

import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ase.common.taggingEvent.ETags;

@RestController
@RequestMapping(path = "api/v1/tag")
public class TaggingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final TaggingService taggingService;

    @Autowired
    public TaggingController(TaggingService taggingService) {
        this.taggingService = taggingService;
    }

    @PostMapping("/add/{eventId}/{userId}/{addEventTag}")  //add new eventTag to event for user
    public TaggingEvent addEventTag(@PathVariable String eventId, @PathVariable String userId,
            @PathVariable ETags addEventTag) {
        LOGGER.info("POST api/v1/tag/add/{}/{}/{}", eventId, userId, addEventTag);
        return taggingService.addNewTaggingEvent(userId, eventId, addEventTag);
    }

    @GetMapping("/event/{eventId}/{userId}") //returns event with tags
    public TaggingEvent getEvent(@PathVariable String eventId, @PathVariable String userId) {
        LOGGER.info("GET api/v1/tag/event/{}/{}", eventId, userId);
        return taggingService.getEventById(eventId, userId);
    }

    @PutMapping("/removeTag/{eventId}/{userId}/{eventTag}")
    public TaggingEvent removeEventTag(@PathVariable String eventId, @PathVariable String userId,
            @PathVariable ETags eventTag) {
        LOGGER.info("DELETE api/v1/tag/removeTag/{}/{}/{}", eventId, userId, eventTag);
        return taggingService.removeTag(userId, eventId, eventTag);
    }

    @DeleteMapping("/removeEvent/{eventId}/{userId}")
    public void removeTaggingEvent(@PathVariable String eventId, @PathVariable String userId) {
        LOGGER.info("DELETE api/v1/tag/removeEvent/{}/{}", eventId, userId);
        taggingService.removeTaggingEvent(userId, eventId);
    }
}
