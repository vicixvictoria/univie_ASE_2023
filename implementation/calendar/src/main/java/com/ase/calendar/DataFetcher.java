package com.ase.calendar;

import com.ase.common.event.Event;
import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class DataFetcher {

    /**
     * class gets data from EventService and BookmarkService
     */


    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    public static List<CalendarEvent> requestBookmarkedEvents(String userId) {
        String BookmarkServiceURL = "http://bookmarkservice:8080/api/v1/bookmark/bookmarkedEventIds/";

        List<CalendarEvent> bookmarkedEvents = new ArrayList<>();
        String requestUrl = BookmarkServiceURL + userId;
        LOGGER.debug("requesting bookmark event data");
        RestTemplate restTemplate = new RestTemplate();
        try {
            List idResponse = restTemplate.getForObject(requestUrl,
                    List.class);  // get eventids for user
            for (int i = 0; i < Objects.requireNonNull(idResponse).size(); i++) {
                String eventId = idResponse.get(i).toString();
                try {
                    String eventUrl = "http://event:8080/api/v1/events/event/" + eventId;
                    Event event = restTemplate.getForObject(eventUrl, Event.class);
                    if (event != null) {
                        bookmarkedEvents.add(new CalendarEvent(event.eventName(), event.date(),
                                event.description()));
                    }
                } catch (Exception e) {
                    LOGGER.error("Did not fetch from events");
                    return Collections.emptyList();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Did not fetch from bookmarked events");
            return Collections.emptyList();
        }
        return bookmarkedEvents;
    }

    public static List<CalendarEvent> requestRegisteredEvent(String userId) {

        String attendanceUrl = "http://attendance:8080/api/v1/attendeeEventList/" + userId;
        RestTemplate restTemplate = new RestTemplate();
        List<CalendarEvent> registeredEvents = new ArrayList<>();
        try {

            List<String> registeredEventResponseIds = restTemplate.getForObject(attendanceUrl,
                    List.class); //TODO change to the user object

            for (int i = 0; i < Objects.requireNonNull(registeredEventResponseIds).size(); i++) {
                String eventId = registeredEventResponseIds.get(i).toString();
                try {
                    String eventUrl = "http://event:8080/api/v1/events/event/" + eventId;
                    Event event = restTemplate.getForObject(eventUrl, Event.class);
                    if (event != null) {
                        registeredEvents.add(new CalendarEvent(event.eventName(), event.date(),
                                event.description()));
                    }
                } catch (Exception e) {
                    LOGGER.error("Did not fetch from events");
                    return Collections.emptyList();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Did not fetch data from attendance service");
        }
        return registeredEvents;
    }

}
