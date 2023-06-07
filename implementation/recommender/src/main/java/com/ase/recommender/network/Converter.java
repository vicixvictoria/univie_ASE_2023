package com.ase.recommender.network;

import com.ase.common.event.Event;
import com.ase.recommender.data.EventType;

/**
 * The Converter class is used for converting RabbitMQ messages.
 */
public class Converter {

    /**
     * Converts an Event object to an EventType object.
     *
     * @param event The Event object to convert.
     * @return The converted EventType object.
     */
    public EventType getEventType(Event event) {
        return new EventType(event.eventID(), event.type());
    }
}
