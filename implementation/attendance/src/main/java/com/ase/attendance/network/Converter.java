package com.ase.attendance.network;

import com.ase.attendance.data.EventCapacity;
import com.ase.common.event.Event;

/**
 * The Converter class is used for converting RabbitMQ messages.
 */
public class Converter {

    /**
     * Converts an Event object to EventCapacity object.
     *
     * @param event The Event object to convert.
     * @return An EventCapacity object with the event's ID and capacity.
     */
    public EventCapacity getEventCapacity(Event event) {
        return new EventCapacity(event.eventID(), event.capacity());
    }
}
