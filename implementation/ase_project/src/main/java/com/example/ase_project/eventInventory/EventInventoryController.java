package com.example.ase_project.eventInventory;

import com.example.ase_project.event.Event;
import com.example.ase_project.event.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(path = "api/v1/eventInventory")
public class EventInventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventService eventService;
    private final EventInventoryService eventInventoryService;

    @Autowired
    public EventInventoryController(EventService eventService, EventInventoryService eventInventoryService) {
        this.eventService = eventService;
        this.eventInventoryService = eventInventoryService;
    }

    @GetMapping(value = "/getEventInventory")
    public EventInventory getEventInventory(String organizerID) {
        LOGGER.info("GET api/v1/eventInventory");
        return eventInventoryService.getEventInventoryByOrganizerID(organizerID);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/createEventInventory")
    public EventInventory createEventInventory(@RequestBody EventInventory eventInventory) {
        LOGGER.info("POST api/v1/eventInventory: {} ", eventInventory);
        eventInventoryService.createEventInventory(eventInventory);
        return eventInventory;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/updateEventInventory")
    public EventInventory updateEventInventory(@RequestBody EventInventory eventInventory) {
        LOGGER.info("PUT api/v1/eventInventory: {} ", eventInventory);
        eventInventoryService.updateEventInventory(eventInventory);
        return eventInventory;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/updateEventInventoryEvents")
    public Event updateEventInventoryEvents(@RequestBody Event event) {
        LOGGER.info("PUT api/v1/eventInventory: {} ", event);
        eventInventoryService.updateEventInventoryEvents(event);
        return event;
    }

}
