package com.ase.recommender.network;

import com.ase.common.RabbitMQMessage;
import com.ase.common.bookmarkEvent.BookmarkEventMessage;
import com.ase.common.event.Event;
import com.ase.recommender.RecommenderService;
import com.ase.recommender.data.EventType;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Subscriber {


    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final RecommenderService service;

    @Autowired
    public Subscriber(RecommenderService service) {
        this.service = service;
    }

    public void eventTypeConsumer(RabbitMQMessage<Event> eventMessage) {
        LOGGER.info("Received an event message");

        Converter converter = new Converter();
        EventType eventType = converter.getEventType(eventMessage.getContent());

        switch (eventMessage.getMessageType()) {
            case NEW -> service.addEventType(eventType.getID(), eventType.getEventType());
            case UPDATE -> service.updateEventType(eventType.getID(), eventType.getEventType());
            case DELETE -> service.removeEventType(eventType.getID(), eventType.getEventType());
        }
    }
    public void bookmarkEventConsumer(RabbitMQMessage<BookmarkEventMessage> bookmarkEventMessage) {
        switch (bookmarkEventMessage.getMessageType()) {
            case UPDATE -> service.addInterest(bookmarkEventMessage.getContent().userId(),
                    bookmarkEventMessage.getContent().eventId());
            case DELETE -> service.removeInterest(bookmarkEventMessage.getContent().eventId(),
                    bookmarkEventMessage.getContent().userId());
        }
    }


}

