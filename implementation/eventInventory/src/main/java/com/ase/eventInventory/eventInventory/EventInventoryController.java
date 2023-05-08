package com.ase.eventInventory.eventInventory;

import java.lang.invoke.MethodHandles;

import com.ase.eventInventory.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/eventInventory")
public class EventInventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    //private final EventService eventService;
    private final EventInventoryService eventInventoryService;

    @Autowired
    public EventInventoryController(EventInventoryService eventInventoryService) {
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{eventInventoryID}")
    public void deleteEventInventory(@PathVariable String eventInventoryID) {
        LOGGER.info("DELETE api/v1/eventInventory: {} ", eventInventoryID);
        eventInventoryService.deleteEvent(eventInventoryID);
    }

}

