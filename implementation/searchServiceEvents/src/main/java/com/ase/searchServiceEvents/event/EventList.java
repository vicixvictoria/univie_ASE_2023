package com.ase.searchServiceEvents.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventList {

    private final List<Event> events;

    public EventList(){
        events = new ArrayList<Event>();
    }

    public EventList(List<Event> events){
        this.events = events;
    }

    public EventList(EventList eventList){
        this.events = eventList.getEvents();
    }

    public static EventList emptyList() {
        return new EventList();
    }

    public List<Event> getEvents(){
        return this.events;
    }

    public void add(Event event) {
        events.add(event);
    }

    public void add(EventList eventList) {
        for (Event elem : eventList.getEvents()) {
            this.add(elem);
        }
    }

    public void add(List<Event> eventList) {
        for (Event elem : eventList) {
            this.add(elem);
        }
    }


}
