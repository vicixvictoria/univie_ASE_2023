package com.ase.attendance;

import com.ase.attendance.data.Attendees;
import com.ase.attendance.data.EventCapacity;
import com.ase.attendance.network.Publisher;
import com.ase.attendance.repository.IAttendanceRepository;
import com.ase.common.attendance.AttendeeEventList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * The AttendanceServiceTest class contains unit tests for AttendanceService.
 */
public class AttendanceServiceTest {

    @InjectMocks
    private AttendanceService service;

    @Mock
    private IAttendanceRepository repository;

    @Mock
    private Publisher publisher;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the register method of AttendanceService.
     */
    @Test
    public void testRegister() {
        Attendees attendees = new Attendees("event1", 50);
        when(repository.getByEventID("event1")).thenReturn(attendees);
        assertEquals(1, service.register("user1", "event1"));
        verify(publisher).newAttendee("user1", "event1");
    }

    /**
     * Tests the deregister method of AttendanceService.
     */
    @Test
    public void testDeregister() {
        Attendees attendees = new Attendees("event1", 50);
        attendees.addAttendee("user1");
        when(repository.getByEventID("event1")).thenReturn(attendees);
        assertEquals(0, service.deregister("user1", "event1"));
        verify(publisher).deleteAttendee("user1", "event1");
    }

    /**
     * Tests the newCapacity method of AttendanceService.
     */
    @Test
    public void testNewCapacity() {
        EventCapacity capacity = new EventCapacity("event1", 50);
        service.newCapacity(capacity);
        verify(repository).save(any(Attendees.class));
    }

    /**
     * Tests the deleteCapacity method of AttendanceService.
     */
    @Test
    public void testDeleteCapacity() {
        EventCapacity capacity = new EventCapacity("event1", 50);
        Attendees attendees = new Attendees("event1", 50);
        when(repository.getByEventID("event1")).thenReturn(attendees);
        service.deleteCapacity(capacity);
        verify(repository).delete(attendees);
    }

    /**
     * Tests the attendance method of AttendanceService.
     */
    @Test
    public void testAttendance() {
        Attendees attendees = new Attendees("event1", 50);
        attendees.addAttendee("user1");
        when(repository.getByEventID("event1")).thenReturn(attendees);
        assertEquals(1, service.attendance("event1"));
    }

    /**
     * Tests the getEventList method of AttendanceService.
     */
    @Test
    public void testGetEventList() {
        Attendees attendees1 = new Attendees("event1", 50);
        attendees1.addAttendee("user1");
        Attendees attendees2 = new Attendees("event2", 50);
        attendees2.addAttendee("user2");
        List<Attendees> allAttendees = Arrays.asList(attendees1, attendees2);
        when(repository.findAll()).thenReturn(allAttendees);
        AttendeeEventList eventList = service.getEventList("user1");
        assertEquals("user1", eventList.getUserID());
        assertEquals(1, eventList.getEventIDs().size());
        assertEquals("event1", eventList.getEventIDs().get(0));
    }
}