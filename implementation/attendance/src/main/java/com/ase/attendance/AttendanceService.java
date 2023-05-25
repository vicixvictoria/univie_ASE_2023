package com.ase.attendance;

import com.ase.attendance.data.Attendees;
import com.ase.attendance.data.EventCapacity;
import com.ase.attendance.network.Publisher;
import com.ase.attendance.repository.IAttendanceRepository;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final IAttendanceRepository repository;
    private final Publisher publisher;

    @Autowired
    public AttendanceService(IAttendanceRepository repository, Publisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    /**
     * This method is called for attending an event with it's eventID
     *
     * @param eventID for event identification
     * @return Attendee count
     */
    public int attend(String userID, String eventID) {
        repository.getByEventID(eventID).addAttendee(userID);
        int count = repository.getByEventID(eventID).getAttendees().size();
        publisher.newAttendee(userID, eventID);
        return count;
    }

    public void newCapacity(EventCapacity eventCapacity) {
        repository.save(new Attendees(eventCapacity.getID(), eventCapacity.getCapacity()));
    }

    public void updateCapacity(EventCapacity eventCapacity) {
        repository.getByEventID(eventCapacity.getID()).setCapacity(eventCapacity.getCapacity());
    }

    public void deleteCapacity(EventCapacity eventCapacity) {
        repository.delete(repository.getByEventID(eventCapacity.getID()));
    }

    /**
     * This method is called for getting the attendee count of a given event with it's eventID
     *
     * @param eventID for event identification
     * @return Attendee count
     */
    public int attendance(String eventID) {
        return repository.getByEventID(eventID).getAttendees().size();
    }
}
