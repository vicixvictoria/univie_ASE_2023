package com.example.ase_project.taggingEvent;

import com.example.ase_project.taggingEvent.repository.ITaggingRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class TaggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ITaggingRepository repository;

    @Autowired
    public TaggingService(ITaggingRepository repository) {
        this.repository = repository;
    }


    public TaggingEvent getEventById(String eventId, String attendeeId) {
        LOGGER.debug("get TaggingEvent by eventId and attendeeId");
        return repository.getTaggingEventByEventIdAndUserId(eventId, attendeeId);
    }


    /**
     * method is called to add a New TaggingEvent anr returns new TaggingEvent
     *
     * @param  userId, eventId, etags
     * @return TaggingEvent
     */
    public TaggingEvent addNewTaggingEvent(String userId, String eventId, ETags eTags) {
        LOGGER.debug("add New TaggingEvent");
        if (repository.existsByEventIdAndUserId(eventId, userId)) { //in case Event already exists in database
            LOGGER.debug("TaggingEvent changed");
            TaggingEvent taggingEvent = repository.getTaggingEventByEventIdAndUserId(eventId, userId);
            repository.deleteByEventIdAndUserId(eventId, userId);
            TaggingEvent newTaggingEvent = new TaggingEvent(taggingEvent);
            newTaggingEvent.addEventTag(eTags);
            repository.save(newTaggingEvent);
            return newTaggingEvent;
        } else {   //in case new TaggingEvent must be created in DB
            LOGGER.debug("Create new TaggingEvent");
            TaggingEvent newTaggingEvent = new TaggingEvent(userId, eventId, eTags);
            repository.save(newTaggingEvent);
            return newTaggingEvent;
        }
    }

    /**
     * method is called to delete a Tag and changes the TaggingEvent, returns TaggingEvent
     *
     * @param   userId,  eventId,  eTags
     * @return TaggingEvent
     */
    @Transactional
    public TaggingEvent removeTag(String userId, String eventId, ETags eTags) {
        LOGGER.debug("Remove Tag from TaggingEvent");
        TaggingEvent changedTaggingEvent = repository.getTaggingEventByEventIdAndUserId(eventId, userId);
        if (changedTaggingEvent.getEventTags() == null) {  //removes DB entry not longer needed
            LOGGER.debug("TaggingEvent deleted from Database");
            repository.deleteAllByEventIdAndUserId(eventId, userId);
            return null;
        } else {
            changedTaggingEvent.removeEventTag(eTags);
            repository.save(changedTaggingEvent);
            return changedTaggingEvent;
        }
    }

    /**
     * removes TaggingEvent from Database
     *
     * @param   userId,  eventId
     * @return void
     */
    @Transactional
    public void removeTaggingEvent(String userId, String eventId) {
        LOGGER.debug("Delete TaggingEvent");
        repository.deleteAllByEventIdAndUserId(eventId, userId);
    }
}