package com.ase.attendance.integration;

import com.ase.attendance.business.AttendanceService;
import com.ase.attendance.domain.EventCapacity;
import com.ase.common.RabbitMQMessage;
import com.ase.common.event.Event;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Subscriber class is responsible for consuming messages from RabbitMQ.
 */
@Component
public class Subscriber {

    private final AttendanceService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    /**
     * Constructs a new instance of Subscriber.
     *
     * @param service the AttendanceService to be used by this subscriber.
     */
    @Autowired
    public Subscriber(AttendanceService service) {
        this.service = service;
    }

    /**
     * Processes messages received from RabbitMQ.
     *
     * @param eventMessage the received RabbitMQMessage containing an Event.
     */
    public void capacityConsumer(RabbitMQMessage<Event> eventMessage) {
        LOGGER.info("Received an event message");

        Converter converter = new Converter();
        EventCapacity eventCapacity = converter.getEventCapacity(eventMessage.getContent());

        switch (eventMessage.getMessageType()) {
            case NEW -> service.newCapacity(eventCapacity);
            case UPDATE -> service.updateCapacity(eventCapacity);
            case DELETE -> service.deleteCapacity(eventCapacity);
        }
    }
}
