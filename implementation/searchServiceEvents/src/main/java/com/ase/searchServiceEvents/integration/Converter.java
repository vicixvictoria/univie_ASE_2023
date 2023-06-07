package com.ase.searchServiceEvents.integration;

import com.ase.common.event.EEventType;
import com.ase.common.event.Event;
import com.ase.searchServiceEvents.domain.EEventTypes;

public class Converter {

    public EEventTypes getLocalEventType(EEventType eventType) {
        return switch (eventType) {
            case FOOD -> EEventTypes.FOOD;
            case HEALTH -> EEventTypes.HEALTH;
            case ENTERTAINMENT -> EEventTypes.ENTERTAINMENT;
        };
    }

    public com.ase.searchServiceEvents.domain.Event toEvent(Event event){
        return new com.ase.searchServiceEvents.domain.Event(event.eventID(),getLocalEventType(event.type()),event.capacity(), event.date(), event.description(), event.organizerID(), event.eventName());
    }

}
