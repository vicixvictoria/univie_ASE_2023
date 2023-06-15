package com.ase.recommender.integration;

import com.ase.common.RabbitMQMessage;
import com.ase.common.bookmarkEvent.BookmarkEventMessage;
import com.ase.common.event.Event;
import com.ase.recommender.business.RecommenderService;
import com.ase.recommender.domain.EventType;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Subscriber class handles incoming messages from RabbitMQ.
 */
@Component
public class Subscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final RecommenderService service;

    /**
     * Constructs a new instance of Subscriber with the specified RecommenderService.
     *
     * @param service The RecommenderService instance to use for handling messages.
     */
    @Autowired
    public Subscriber(RecommenderService service) {
        this.service = service;
    }

    /**
     * Handles the incoming event type message from RabbitMQ.
     *
     * @param eventMessage The RabbitMQ message containing the event type.
     */
    public void eventTypeConsumer(RabbitMQMessage<Event> eventMessage) {
        LOGGER.info("Received an event message");
        Converter converter = new Converter();
        EventType eventType = converter.getEventType(eventMessage.getContent());
        switch (eventMessage.getMessageType()) {
            case NEW -> service.addEventType(eventType.getID(), eventMessage.getContent().type());
            case UPDATE -> service.updateEventType(eventType.getID(), eventMessage.getContent().type());
            case DELETE -> service.removeEventType(eventType.getID(), eventMessage.getContent().type());
        }
    }

    /**
     * Handles the incoming bookmark event message from RabbitMQ.
     *
     * @param bookmarkEventMessage The RabbitMQ message containing the bookmark event.
     */
    public void bookmarkEventConsumer(RabbitMQMessage<BookmarkEventMessage> bookmarkEventMessage) {
        LOGGER.info("Received an bookmark message");
        switch (bookmarkEventMessage.getMessageType()) {
            case NEW -> service.addInterest(bookmarkEventMessage.getContent().userId(),
                    bookmarkEventMessage.getContent().eventId());
            case DELETE -> service.removeInterest(bookmarkEventMessage.getContent().eventId(),
                    bookmarkEventMessage.getContent().userId());
        }
    }


}

