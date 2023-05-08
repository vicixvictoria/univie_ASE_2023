package com.ase.searchServiceEvents;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;

import com.ase.searchServiceEvents.event.EEventTypes;
import com.ase.searchServiceEvents.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/searchService")
public class SearchServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    //private final EventService eventService;
    // private final EventInventoryService eventInventoryService;

    @Autowired
    public SearchServiceController() {
    }

    //TODO: Events über Service ansprechen nicht Endpoint

    @GetMapping(value = "/getEvents")
    public List<Event> getAllEvents() {
        LOGGER.info("GET api/v1/events");
        return null;
    }

    @GetMapping("/{organizerID}")
    public List<Event> getEventsByOrganizerID(@PathVariable String organizerID) {
        LOGGER.info("GET api/v1/events");
        return null;
    }

    @GetMapping("/{date}")
    public List<Event> getEventsByDate(@PathVariable Date date) {
        LOGGER.info("GET api/v1/eventsby {}", date);
        return null;
    }

    @GetMapping("/type")
    public List<Event> getEventsByType(@PathVariable EEventTypes type) {
        LOGGER.info("GET api/v1/eventsby {}", type);
        return null;
    }

    @GetMapping("/capacity")
    public List<Event> getEventsByCapacity(@PathVariable int capacity) {
        LOGGER.info("GET api/v1/eventsby {}", capacity);
        return null;
    }

}
