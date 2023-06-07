package com.ase.searchServiceEvents.presentation;

import com.ase.searchServiceEvents.business.SearchServiceService;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Date;

import com.ase.searchServiceEvents.domain.EEventTypes;
import com.ase.searchServiceEvents.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/searchService")
public class SearchServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final SearchServiceService searchServiceService;

    @Autowired
    public SearchServiceController(SearchServiceService searchServiceService) {
        this.searchServiceService = searchServiceService;
    }

    @GetMapping()
    public Collection<Event> getAllEvents() {
        LOGGER.info("GET api/v1/searchService");
        return searchServiceService.getAll();
    }

    @GetMapping(value = "/organizer/{organizerID}")
    public Collection<Event> getEventsByOrganizerID(@PathVariable String organizerID) {
        LOGGER.info("GET api/v1/events/{}", organizerID);
        return searchServiceService.getAllEventsByorganizerID(organizerID);
    }


    @GetMapping("/event/{eventID}")
    public Event getEventsByID(@PathVariable String eventID) {
        LOGGER.info("GET api/v1/searchService/{}", eventID);
        return searchServiceService.getEventsByID(eventID);
    }

    @GetMapping(value = "/capacity/{capacity}")
    public Collection<Event> getEventsByCapacity(@PathVariable int capacity) {
        LOGGER.info("GET api/v1/searchService/capacity/{}", capacity);
        return searchServiceService.getEventsByCapacity(capacity);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/eventName/{eventName}")
    public Collection<Event> getEventsByEventName(@PathVariable String eventName) {
        LOGGER.info("GET api/v1/searchService/eventName/{}", eventName);
        return searchServiceService.getEventsByName(eventName);
    }

    @GetMapping(value = "/description/{description}")
    public Collection<Event> getEventsByDescription(@PathVariable String description) {
        LOGGER.info("GET api/v1/searchService/description/{}", description);
        return searchServiceService.getEventsByName(description);
    }

    @GetMapping(value = "/date/{date}")
    public Collection<Event> getEventsByDate(@PathVariable Date date) {
        LOGGER.info("GET api/v1/searchService/date/{}", date);
        return searchServiceService.getEventsByDate(date);
    }

    @GetMapping(value = "/type/{type}")
    public Collection<Event> getEventsByType(@PathVariable EEventTypes type) {
        LOGGER.info("GET api/v1/searchService/type/{}", type);
        return searchServiceService.getEventsByType(type);
    }

    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

}
