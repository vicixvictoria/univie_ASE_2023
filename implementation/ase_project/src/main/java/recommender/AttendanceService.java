package attendance;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    static Map<String, ArrayList<String>> eventAttendeeMap = new HashMap<>();
    static String attendeeID = "100";

    /**
     * This method is called for attending an event with it's eventID
     *
     * @param eventID for event identification
     * @return Attendee count
     */
    public static int attend(String eventID) {
        ArrayList<String> attendees;
        if (eventAttendeeMap.get(eventID) == null) {
            attendees = new ArrayList<>();
            attendees.add(attendeeID);
            eventAttendeeMap.put(eventID, attendees);
            LOGGER.info("attendee registered for new event");
        } else {
            attendees = eventAttendeeMap.get(eventID);
            if (!attendees.contains(attendeeID)) {
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
     *
     * @param eventID for event identification
     * @return Attendee count
     */
    public static int attendance(String eventID) {
        if (eventAttendeeMap.get(eventID) == null) {
            return 0;
        } else {
            return eventAttendeeMap.get(eventID).size();
        }
    }
}
