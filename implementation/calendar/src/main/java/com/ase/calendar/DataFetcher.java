package com.ase.calendar;

import com.ase.common.attendance.AttendeeEventList;
import com.ase.common.event.Event;
import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DataFetcher {

    /**
     * class gets data from EventService and BookmarkService
     */

    //@Value("${EVENT-ENDPOINT}")
    //private String GET_EVENT_ENDPOINT;

    //@Value("${BOOKMARK-ENDPOINT}")
    //private String GET_BOOKMARK_ENDPOINT;
    //@Value("${ATTENDANCE-ENDPOINT}")
    //private  String GET_ATTENDANCE_ENDPOINT;

    public DataFetcher(){

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    public List<CalendarEvent> requestBookmarkedEvents(String userId) {
        //String BookmarkServiceURL = GET_BOOKMARK_ENDPOINT+"/api/v1/bookmark/bookmarkedEventIds/";
        String BookmarkServiceURL = "http://bookmarkservice:8080/api/v1/bookmark/bookmarkedEventIds/"+userId;
        List<CalendarEvent> bookmarkedEvents = new ArrayList<>();
        String requestUrl = BookmarkServiceURL;
        LOGGER.info("requesting bookmark event data: " + BookmarkServiceURL);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<String>> response = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<String>>() {
                    }
            );
            List<String> idResponse = response.getBody();
            LOGGER.info(idResponse.toString());
            for (int i = 0; i < Objects.requireNonNull(idResponse).size(); i++) {
                String eventId = idResponse.get(i).toString();
                try {
                    String GET_EVENT_ENDPOINT = "http://event:8080/api/v1/events/event/"+ eventId;
                    LOGGER.info("requesting event data " + GET_EVENT_ENDPOINT);
                    RestTemplate EventrestTemplate = new RestTemplate();
                    Event event = EventrestTemplate.getForObject(GET_EVENT_ENDPOINT, Event.class);
                    if (event != null) {
                        bookmarkedEvents.add(new CalendarEvent(event.eventName(), event.date(),
                                event.description()));
                    }
                } catch (Exception e) {
                    LOGGER.error("Did not fetch from events");
                    Date date = new Date();
                    for (String s : idResponse) {
                        bookmarkedEvents.add(new CalendarEvent(s, date, ""));
                    }
                    return Collections.emptyList();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Did not fetch from bookmarked events");
            return Collections.emptyList();
        }
        return bookmarkedEvents;
    }

    public List<CalendarEvent> requestRegisteredEvent(String userId) {
        String GET_ATTENDANCE_ENDPOINT ="http://attendance:8080";
        String attendanceUrl = GET_ATTENDANCE_ENDPOINT+"/api/v1/attendeeEventList/" + userId;
        RestTemplate restTemplate = new RestTemplate();
        List<CalendarEvent> registeredEvents = new ArrayList<>();
        try {
            ResponseEntity<AttendeeEventList> response = restTemplate.exchange(
                    attendanceUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<AttendeeEventList>() {}
            );
            List<String> registeredEventResponseIds = response.getBody().eventIDs();
            LOGGER.info("Events from attendance ", registeredEventResponseIds.toString());
            for (int i = 0; i < Objects.requireNonNull(registeredEventResponseIds).size(); i++) {
                String eventId = registeredEventResponseIds.get(i).toString();
                try {
                    String GET_EVENT_ENDPOINT ="http://event:8080";
                    String eventUrl = GET_EVENT_ENDPOINT+"/api/v1/events/event/" + eventId;
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
