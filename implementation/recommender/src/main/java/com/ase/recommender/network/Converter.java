package com.ase.recommender.network;

import com.ase.common.event.Event;
import com.ase.recommender.data.EventType;

public class Converter {

    public EventType getEventType(Event event) {
        return new EventType(event.eventID(), event.type());
    }
}
