package com.example.ase_project.attendance;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;

@Service
public class AttendanceService {
    static Map<Long,ArrayList<Long>> eventAttendeeMap = new HashMap<>();
    static long attendeeID = 100;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method is called for attending an event with it's eventID
     * @param eventID for event identification
     * @return Attendee count
     */
    public static int attend(Long eventID) {
        ArrayList<Long> attendees;
        if(eventAttendeeMap.get(eventID) == null) {
            attendees = new ArrayList<>();
            attendees.add(attendeeID);
            eventAttendeeMap.put(eventID, attendees);
            LOGGER.info("attendee registered for new event");
        }
        else {
            attendees = eventAttendeeMap.get(eventID);
            if(!attendees.contains(attendeeID)) {
                attendees.add(attendeeID);
                LOGGER.info("attendee is already registered");
            }
            eventAttendeeMap.put(eventID, attendees);
            LOGGER.info("attendee registered for existing event");
        }
        return eventAttendeeMap.get(eventID).size();
    }

    /**
     * This method is called for getting the attendee count of a given event with it's eventID
     * @param eventID for event identification
     * @return Attendee count
     */
    public static int attendance(Long eventID) {
        if (eventAttendeeMap.get(eventID) == null) {
            return 0;
        }
        else return eventAttendeeMap.get(eventID).size();
    }
}
