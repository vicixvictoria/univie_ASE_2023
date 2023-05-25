package com.ase.attendance.network;

import com.ase.attendance.AttendanceService;
import com.ase.attendance.data.EventCapacity;
import com.ase.common.RabbitMQMessage;
import com.ase.common.event.Event;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Subscriber {

    private final AttendanceService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());


    @Autowired
    public Subscriber(AttendanceService service) {
        this.service = service;
    }

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
