package com.example.ase_project.taggingEvent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TaggingEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Id
    private String taggingEventId;

    private String userId;
    private String eventId;

    private List<ETags> eventTags = new ArrayList<>();

    public TaggingEvent(String userId, String eventId, ETags eventTags) {
        LOGGER.debug("TaggingEvent Entity created userId: {}, eventId: {}, eventTags: {}", userId, eventId, eventTags);
        this.eventId = eventId;
        if (eventTags != null) {
            this.eventTags.add(eventTags);
        }
        this.userId = userId;
        this.taggingEventId = UUID.randomUUID().toString().replace("-", "");
    }

    public TaggingEvent(TaggingEvent taggingEvent) {
        LOGGER.debug("TaggingEvent Entity created");
        this.eventId = taggingEvent.getEventId();
        this.eventTags = taggingEvent.getEventTags();
        this.userId = taggingEvent.getUserId();
        this.taggingEventId = UUID.randomUUID().toString().replace("-", "");
    }

    public TaggingEvent() {
        LOGGER.debug("TaggingEvent Entity created");
    }

    public void addEventTag(ETags tags) {
        this.eventTags.add(tags);
    }

    public String getTaggingEventId() {
        return taggingEventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<ETags> getEventTags() {
        return this.eventTags;
    }

    public void setEventTags(List<ETags> eventTags) {
        this.eventTags = eventTags;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEventTag(ETags eventTag) {
        this.eventTags.add(eventTag);
    }

    public void removeEventTag(ETags eventTag) {
        this.eventTags.remove(eventTag);
    }
}
