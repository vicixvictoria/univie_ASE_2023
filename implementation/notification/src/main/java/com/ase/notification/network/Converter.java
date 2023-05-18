package com.ase.notification.network;

import com.ase.common.event.Event;
import com.ase.notification.model.data.RawEvent;

public class Converter {

    public RawEvent getLocalEvent(Event event) {
        return new RawEvent(event.eventID(), event.eventName(), event.description(), event.date());
    }
}
