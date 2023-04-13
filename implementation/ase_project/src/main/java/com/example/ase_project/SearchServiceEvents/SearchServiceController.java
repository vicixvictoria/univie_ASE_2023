package com.example.ase_project.SearchServiceEvents;

import com.example.ase_project.Event.EEventTypes;
import com.example.ase_project.Event.Event;
import com.example.ase_project.Event.EventService;
import com.example.ase_project.EventInventory.EventInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/searchService")
public class SearchServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventService eventService;
    private final EventInventoryService eventInventoryService;

    @Autowired
    public SearchServiceController(EventService eventService, EventInventoryService eventInventoryService) {
        this.eventService = eventService;
        this.eventInventoryService = eventInventoryService;
    }

    //TODO: Events über Service ansprechen nicht Endpoint

    @GetMapping(value = "/getEvents")
    public List<Event> getAllEvents() {
        LOGGER.info("GET api/v1/events");
        return eventService.getAll();
    }

    @GetMapping("/{organizerID}")
    public List<Event> getEventsByOrganizerID(@PathVariable Long organizerID) {
        LOGGER.info("GET api/v1/events");
        return eventService.getAllEventsByorganizerID(organizerID);
    }

    @GetMapping("/{date}")
    public List<Event> getEventsByDate(@PathVariable Date date) {
        LOGGER.info("GET api/v1/eventsby {}", date);
        return eventService.getEventByDate(date);
    }

    @GetMapping("/type")
    public List<Event> getEventsByType(@PathVariable EEventTypes type) {
        LOGGER.info("GET api/v1/eventsby {}", type);
        return eventService.getEventByType(type);
    }

    @GetMapping("/capacity")
    public List<Event> getEventsByCapacity(@PathVariable int capacity) {
        LOGGER.info("GET api/v1/eventsby {}", capacity);
        return eventService.getEventByCapacity(capacity);
    }

}
