package com.ase.attendance.network;

import com.ase.attendance.data.EventCapacity;
import com.ase.common.event.Event;

public class Converter {

    public EventCapacity getEventCapacity(Event event) {
        return new EventCapacity(event.eventID(), event.capacity());
    }
}
