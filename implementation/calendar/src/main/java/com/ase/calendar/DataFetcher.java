package com.ase.calendar;

import com.ase.common.event.Event;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.web.client.RestTemplate;

public class DataFetcher {

    /**
     * class gets data from EventService and BookmarkService
     */

    public static List<CalendarEvent> requestBookmarkedEvents(String userId) {
        String BookmarkServiceURL = "http://bookmark:8080/api/v1/bookmark/bookmarkedEventIds/";

        String requestUrl = BookmarkServiceURL + userId;
        RestTemplate restTemplate = new RestTemplate();
        List idResponse = restTemplate.getForObject(requestUrl,
                List.class);  // get eventids for user

        List<CalendarEvent> bookmarkedEvents = new ArrayList<>(); //refactor to Event with from common service!!!

        for (int i = 0; i < Objects.requireNonNull(idResponse).size(); i++) {
            String eventId = idResponse.get(i).toString();
            String eventUrl = "http://localhost:8082/api/v1/events/event/" + eventId;
            Event event = restTemplate.getForObject(eventUrl, Event.class);
            if (event != null) {
                bookmarkedEvents.add(
                        new CalendarEvent(event.eventName(), event.date(), event.description()));
            }
        }
        return bookmarkedEvents;
    }

    public static List<CalendarEvent> requestRegisteredEvent(String userId) throws ParseException {
        //get event for userid

        /* String eventUrl = "";  //TODO
        RestTemplate restTemplate = new RestTemplate();
        List<Event> registeredEventResponse = restTemplate.getForObject(eventUrl, List.class);
        List<CalendarEvent>registeredEvents = new ArrayList<>();

        for (int i=0; i<=Objects.requireNonNull(registeredEventResponse).size(); i++){
            registeredEvents.add(new CalendarEvent(registeredEventResponse.get(i).eventName(), registeredEventResponse.get(i).date()));
        }
        return registeredEvents;*/

        //Mock for testing
        List<CalendarEvent> registeredEventsMock = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        registeredEventsMock.add(new CalendarEvent("TestEvent1", format.parse("16.06.2023 19:00"),
                "Es ist toll hier"));
        return registeredEventsMock;
    }

}
