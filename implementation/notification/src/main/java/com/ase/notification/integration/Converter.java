package com.ase.notification.integration;

import com.ase.common.event.Event;
import com.ase.notification.domain.RawEvent;

public class Converter {

    public RawEvent getLocalEvent(Event event) {
        return new RawEvent(event.eventID(), event.eventName(), event.description(), event.date());
    }
}
