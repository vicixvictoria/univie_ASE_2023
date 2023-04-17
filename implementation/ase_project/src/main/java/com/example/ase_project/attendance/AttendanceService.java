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
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static int attend(Long eventID) {
        Long attendeeID = (long)100;
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
}
