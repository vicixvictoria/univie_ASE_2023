package com.ase.attendance.business;

import com.ase.attendance.domain.Attendees;
import com.ase.attendance.domain.EventCapacity;
import com.ase.attendance.integration.Publisher;
import com.ase.attendance.dataAccess.IAttendanceRepository;
import com.ase.common.attendance.AttendeeEventList;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The AttendanceService class handles operations related to event attendance.
 */
@Service
public class AttendanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final IAttendanceRepository repository;
    private final Publisher publisher;

    /**
     * Constructs a new instance of AttendanceService.
     *
     * @param repository the AttendanceRepository to be used by this service.
     * @param publisher  the Publisher to be used by this service.
     */
    @Autowired
    public AttendanceService(IAttendanceRepository repository, Publisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    /**
     * Registers a user at an event.
     *
     * @param userID  the ID of the user attending the event.
     * @param eventID the ID of the event being attended.
     * @return the updated count of attendees for the event.
     */
    public int register(String userID, String eventID) {
        Attendees attendees = repository.getByEventID(eventID);
        int count;
        if (attendees != null) {
            LOGGER.info("Event ID {} found in the repository for registration.", eventID);
            attendees.addAttendee(userID);
            repository.save(attendees);
            count = attendees.getAttendees().size();
            LOGGER.info("Attendance count: " + count);
            publisher.newAttendee(userID, eventID);
        } else {
            LOGGER.warn("Event ID {} not found in the repository registration.", eventID);
            count = 0;
        }
        return count;
    }

    /**
     * Deregisters a user at an event.
     *
     * @param userID  the ID of the user attending the event.
     * @param eventID the ID of the event being attended.
     * @return the updated count of attendees for the event.
     */
    public int deregister(String userID, String eventID) {
        int count;
        Attendees attendees = repository.getByEventID(eventID);
        if (attendees != null) {
            LOGGER.info("Event ID {} found in the repository for deregistration.", eventID);
            attendees.removeAttendee(userID);
            repository.save(attendees);
            count = attendees.getAttendees().size();
            publisher.deleteAttendee(userID, eventID);
        } else {
            LOGGER.warn("Event ID {} not found in the repository for deregistration.", eventID);
            count = -1;
        }
        return count;
    }

    /**
     * Records a new event capacity in the system.
     *
     * @param eventCapacity the EventCapacity object to be added.
     */
    public void newCapacity(EventCapacity eventCapacity) {
        repository.save(new Attendees(eventCapacity.getID(), eventCapacity.getCapacity()));
    }

    /**
     * Updates the capacity of an existing event.
     *
     * @param eventCapacity the EventCapacity object with the updated capacity.
     */
    public void updateCapacity(EventCapacity eventCapacity) {
        repository.getByEventID(eventCapacity.getID()).setCapacity(eventCapacity.getCapacity());
    }

    /**
     * Deletes the capacity and attendees record of an event.
     *
     * @param eventCapacity the EventCapacity object to be deleted.
     */
    public void deleteCapacity(EventCapacity eventCapacity) {
        if(repository.getByEventID(eventCapacity.getID()) != null)
        {
            repository.delete(repository.getByEventID(eventCapacity.getID()));
        }
        else LOGGER.warn("Unknown Event ID: " + eventCapacity.getID());
    }

    /**
     * Gets the number of attendees for a given event.
     *
     * @param eventID the ID of the event.
     * @return the number of attendees for the event.
     */
     public int attendance(String eventID) {
         int count;
         Attendees attendees = repository.getByEventID(eventID);
         if (attendees != null) {
             LOGGER.info("Event ID {} found in the repository for attendance.", eventID);
             count = attendees.getAttendees().size();
         } else {
             LOGGER.warn("Event ID {} not found in the repository for attendance.", eventID);
             count = -1;
         }
        return count;
    }

    /**
     * Gets the list of events that a user is attending.
     *
     * @param userID the ID of the user.
     * @return the AttendeeEventList for the user.
     */
     public AttendeeEventList getEventList(String userID) {
        List<Attendees> allAttendees = repository.findAll();
        List<String> eventIDs = new ArrayList<>();

        for (Attendees attendee : allAttendees) {
            if (attendee.getAttendees().contains(userID)) {
                eventIDs.add(attendee.getEventID());
            }
        }
        return new AttendeeEventList(userID, eventIDs);
    }
}
