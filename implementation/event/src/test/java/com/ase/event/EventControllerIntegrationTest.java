package com.ase.event;
import com.ase.event.Controller.EventController;
import com.ase.event.Data.EEventTypes;
import com.ase.event.Data.Event;
import com.ase.event.Service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(EventController.class)
@AutoConfigureMockMvc
public class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllEvents_Returns200() throws Exception {
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", EEventTypes.HEALTH,345,new Date(),"testing","12345","New Event1"));
        events.add(new Event("Event 2", EEventTypes.HEALTH,345,new Date(),"testing","12345","New Event2"));

        when(eventService.getAll()).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].eventName").value("New Event1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].eventName").value("New Event2"));
    }

    @Test
    public void getEventsByOrganizerID_Returns200() throws Exception {
        String organizerID = "12345";
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", EEventTypes.HEALTH,345,new Date(),"testing",organizerID,"Test1"));

        when(eventService.getAllEventsByorganizerID(organizerID)).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/events/organizer/{organizerID}", organizerID))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].eventName").value("Test1"));
    }

    @Test
    public void getEventsByID_Returns200() throws Exception {
        String eventID = "12345";
        Event event = new Event(eventID, EEventTypes.HEALTH,345,new Date(),"testing","12345","Test1");

        when(eventService.getEventsByID(eventID)).thenReturn(event);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/events/event/{eventID}", eventID))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventName").value("Test1"));
    }

    @Test
    public void createEvent_Returns201() throws Exception {
        Event event = new Event("Event 1", EEventTypes.HEALTH,345,new Date(),"testing","12345","New Event");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/events")
                        .content(objectMapper.writeValueAsString(event))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventName").value("New Event"));
    }

    @Test
    public void updateEvent_Returns200() throws Exception {
        Event event = new Event("Event 1", EEventTypes.HEALTH,345,new Date(),"testing","abcdefg","Updated Event");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/events")
                        .content(objectMapper.writeValueAsString(event))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventName").value("Updated Event"));
    }

    @Test
    public void deleteEvent_Returns204() throws Exception {
        String eventID = "12345";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/events/{eventID}", eventID))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getEventsByCapacity_Returns200() throws Exception {
        int capacity = 100;
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", EEventTypes.HEALTH,capacity,new Date(),"testing","12345","Event Name"));

        when(eventService.getEventsByCapacity(capacity)).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/events/capacity/{capacity}", capacity))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].eventName").value("Event Name"));
    }

    @Test
    public void getEventsByEventName_Returns200() throws Exception {
        String eventName = "EventName";
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", EEventTypes.HEALTH,345,new Date(),"testing","12345","Event Name"));

        when(eventService.getEventsByName(eventName)).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/events/eventName/{eventName}", eventName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].eventName").value("Event Name"));
    }

    @Test
    public void getEventsByDescription_Returns200() throws Exception {
        String description = "Description";
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", EEventTypes.HEALTH,345,new Date(),"Description","12345","Event Name"));

        when(eventService.getEventsByName(description)).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/events/description/{description}", description))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].eventName").value("Event Name"));
    }

   /* @Test
    public void getEventsByDate_Returns200() throws Exception {
        Date date = new Date();
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", EEventTypes.HEALTH,345,date,"Description","12345","Event Name"));

        when(eventService.getEventsByDate(date)).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/events/date/{date}", date))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].eventName").value("Event Name"));
    }*/
}
