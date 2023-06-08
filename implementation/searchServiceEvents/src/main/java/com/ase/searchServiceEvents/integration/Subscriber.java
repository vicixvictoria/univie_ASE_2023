package com.ase.searchServiceEvents.integration;

import com.ase.searchServiceEvents.business.SearchServiceService;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ase.common.event.Event;
import com.ase.common.RabbitMQMessage;


/**
 * The Subscriber class is responsible for consuming messages from RabbitMQ.
 * Class defining callback methods, which will be called when a RabbitMQ message arrives.
 */
@Component
public class Subscriber {

    private final SearchServiceService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    @Autowired
    public Subscriber(SearchServiceService service) {
        this.service = service;
    }

    /**
     * Processes messages received from RabbitMQ.
     *
     * @param eventMessage the received RabbitMQMessage containing an Event.
     */
    public void eventConsumer(RabbitMQMessage<Event> eventMessage) {
        LOGGER.info("Received an event message");
        assert eventMessage != null;

        /*Event eventContent = eventMessage.getContent();
        assert eventContent != null;*/

        Converter converter = new Converter();
        com.ase.searchServiceEvents.domain.Event event1 = converter.toEvent(eventMessage.getContent());
        LOGGER.info(String.format("Received a event with id %s", event1.getEventID()));

        switch (eventMessage.getMessageType()) {
            case NEW -> service.createEvent(event1);
            case UPDATE -> service.updateEvent(event1);
            case DELETE -> service.deleteEvent(event1.getEventID());
        }
    }
}
