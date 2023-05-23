package com.ase.event.network;

import com.ase.common.event.EEventType;
import com.ase.event.EEventTypes;
import com.ase.event.Event;

public class Converter {

    public EEventTypes getLocalEventType(EEventType eventType) {
        return switch (eventType) {
            case FOOD -> EEventTypes.FOOD;
            case HEALTH -> EEventTypes.HEALTH;
            case ENTERTAINMENT -> EEventTypes.ENTERTAINMENT;
        };
    }

    public Event getLocalEvent(com.ase.common.event.Event networkEvent) {
        return new Event(networkEvent.eventID(), getLocalEventType(networkEvent.type()),
                networkEvent.capacity(), networkEvent.date(), networkEvent.description(),
                networkEvent.organizerID(),
                networkEvent.eventName());
    }

    public EEventType getNetworkEventType(EEventTypes eventType) {
        return switch (eventType) {
            case ENTERTAINMENT -> EEventType.ENTERTAINMENT;
            case FOOD -> EEventType.FOOD;
            case HEALTH -> EEventType.HEALTH;
        };
    }

    public com.ase.common.event.Event getNetworkEvent(Event event) {
        return new com.ase.common.event.Event(event.getEventID(),
                getNetworkEventType(event.getType()), event.getCapacity(), event.getDate(),
                event.getDescription(), event.getOrganizerID(), event.getEventName());
    }

}
