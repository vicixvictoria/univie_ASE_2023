package com.example.ase_project.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping(path = "api/v1/events")
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/getEvents")
    public Collection<Event> getAllEvents() {
        LOGGER.info("GET api/v1/events");
        return eventService.getAll();
    }

    @GetMapping("/{organizerID}")
    public Collection<Event> getEventsByOrganizerID(@PathVariable Long organizerID) {
        LOGGER.info("GET api/v1/events/{}", organizerID);
        return eventService.getAllEventsByorganizerID(organizerID);
    }


    @GetMapping("/{eventID}")
    public Event getEventsByID(@PathVariable Long eventID) {
        LOGGER.info("GET api/v1/events/{}", eventID);
        return eventService.getEventsByID(eventID);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/createEvent")
    public Event createEvent(@RequestBody Event event) {
        LOGGER.info("POST api/v1/events: {} ", event);
        eventService.createEvent(event);
        return event;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/updateEvent")
    public Event updateEvent(@RequestBody Event event) {
        LOGGER.info("PUT api/v1/events: {} ", event);
        eventService.updateEvent(event);
        return event;
    }

}
