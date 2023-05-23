package com.ase.taggingService;

import com.ase.common.RabbitMQMessage;
import com.ase.common.taggingEvent.TaggingEventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {

    private final TaggingService service;

    @Autowired
    public Subscriber(TaggingService taggingService) {
        this.service = taggingService;
    }

    /**
     * triggers service based on selected method
     */
    public void bookmarkEventConsumer(RabbitMQMessage<TaggingEventMessage> taggingEventMessage) {
        switch (taggingEventMessage.getMessageType()) {
            case UPDATE -> service.addNewTaggingEvent(taggingEventMessage.getContent().userId(),
                    taggingEventMessage.getContent().eventId(),
                    taggingEventMessage.getContent().tags());
            case DELETE -> service.removeTag(taggingEventMessage.getContent().userId(),
                    taggingEventMessage.getContent().userId(),
                    taggingEventMessage.getContent().tags());
        }
    }
}
