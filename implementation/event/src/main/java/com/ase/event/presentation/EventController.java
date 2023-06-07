package com.ase.event.presentation;
import com.ase.event.domain.EEventTypes;
import com.ase.event.domain.Event;
import com.ase.event.business.EventService;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping(path = "api/v1/events")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public Collection<Event> getAllEvents() {
        LOGGER.info("GET api/v1/events");
        return eventService.getAll();
    }

    @GetMapping(value = "/organizer/{organizerID}")
    public Collection<Event> getEventsByOrganizerID(@PathVariable String organizerID) {
        LOGGER.info("GET api/v1/events/{}", organizerID);
        return eventService.getAllEventsByorganizerID(organizerID);
    }


    @GetMapping("/event/{eventID}")
    public Event getEventsByID(@PathVariable String eventID) {
        LOGGER.info("GET api/v1/events/{}", eventID);
        return eventService.getEventsByID(eventID);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Event createEvent(@RequestBody Event event) {
        LOGGER.info("POST api/v1/events: {} ", event.getEventName());
        eventService.createEvent(event);
        return event;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping()
    public Event updateEvent(@RequestBody Event event) {
        LOGGER.info("PUT api/v1/events: {} ", event);
        eventService.updateEvent(event);
        return event;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{eventID}")
    public void deleteEvent(@PathVariable String eventID) {
        LOGGER.info("DELETE api/v1/events: {} ", eventID);
        eventService.deleteEvent(eventID);
    }

    @GetMapping(value = "/capacity/{capacity}")
    public Collection<Event> getEventsByCapacity(@PathVariable int capacity) {
        LOGGER.info("GET api/v1/events/capacity/{}", capacity);
        return eventService.getEventsByCapacity(capacity);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/eventName/{eventName}")
    public Collection<Event> getEventsByEventName(@PathVariable String eventName) {
        LOGGER.info("GET api/v1/events/eventName/{}", eventName);
        return eventService.getEventsByName(eventName);
    }

    @GetMapping(value = "/description/{description}")
    public Collection<Event> getEventsByDescription(@PathVariable String description) {
        LOGGER.info("GET api/v1/events/description/{}", description);
        return eventService.getEventsByName(description);
    }

    @GetMapping(value = "/date/{date}")
    public Collection<Event> getEventsByDate(@PathVariable Date date) {
        LOGGER.info("GET api/v1/events/date/{}", date);
        return eventService.getEventsByDate(date);
    }

    @GetMapping(value = "/type/{type}")
    public Collection<Event> getEventsByType(@PathVariable EEventTypes type) {
        LOGGER.info("GET api/v1/events/type/{}", type);
        return eventService.getEventsByType(type);
    }

    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

}
