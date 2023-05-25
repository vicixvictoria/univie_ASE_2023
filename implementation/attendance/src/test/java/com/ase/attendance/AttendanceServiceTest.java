package com.ase.attendance;

import com.ase.attendance.data.Attendees;
import com.ase.attendance.network.Publisher;
import com.ase.attendance.repository.IAttendanceRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


public class AttendanceServiceTest {

    private AttendanceService attendanceService;
    private IAttendanceRepository repository;
    private Publisher publisher;
    private Attendees attendees;

    @BeforeEach
    public void setup() {
        // Mock dependencies
        repository = Mockito.mock(IAttendanceRepository.class);
        publisher = Mockito.mock(Publisher.class);
        attendees = Mockito.mock(Attendees.class);

        // Create AttendanceService with mocked dependencies
        attendanceService = new AttendanceService(repository, publisher);

        // Stub methods of Attendees
        Mockito.when(attendees.getAttendees()).thenReturn(new ArrayList<>());

        // Stub methods of IAttendanceRepository
        Mockito.when(repository.getByEventID(any(String.class))).thenReturn(attendees);
        Mockito.when(repository.save(any(Attendees.class))).thenReturn(attendees); // If save returns Attendees
        Mockito.doNothing().when(repository).delete(any(Attendees.class));
    }

    @Test
    void testAttendanceMethod() {
        // given
        String eventId = "event1";

        // when
        int attendeeCount = attendanceService.attendance(eventId);

        // then
        assertEquals(0, attendeeCount);
        Mockito.verify(repository, Mockito.times(1)).getByEventID(eventId);
    }
}

