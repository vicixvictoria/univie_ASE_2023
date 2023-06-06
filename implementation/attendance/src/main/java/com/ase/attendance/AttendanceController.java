package com.ase.attendance;

import com.ase.common.attendance.AttendeeEventList;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1")
public class AttendanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final AttendanceService attendanceService;

    /**
     * Constructs a new instance of AttendanceController.
     *
     * @param attendanceService the AttendanceService to be used by this controller.
     */
    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * Register a user for an event.
     *
     * @param userID  the ID of the user attending the event.
     * @param eventID the ID of the event being attended.
     * @return the new attendance count for the event.
     */
    @PostMapping(value = "/register/{userID}/{eventID}")
    public int register(@PathVariable String userID, String eventID) {
        LOGGER.info("Request: Register for an event");
        return attendanceService.register(userID, eventID);
    }

    /**
     * Deregister a user for an event.
     *
     * @param userID  the ID of the user attending the event.
     * @param eventID the ID of the event being attended.
     * @return the new attendance count for the event.
     */
    @PostMapping(value = "/deregister/{userID}/{eventID}")
    public int deregister(@PathVariable String userID, String eventID) {
        LOGGER.info("Request: Register for an event");
        return attendanceService.deregister(userID, eventID);
    }

    /**
     * Returns the attendance count for a given event.
     *
     * @param eventID the ID of the event.
     * @return the attendance count for the event.
     */
    @GetMapping("/attendance/{eventID}")
    public int attendance(@PathVariable String eventID) {
        LOGGER.info("Request: Getting attendance count");
        return attendanceService.attendance(eventID);
    }

    /**
     * Returns a list of events that a given user is attending.
     *
     * @param userID the ID of the user.
     * @return the AttendeeEventList for the user.
     */
    @GetMapping ("/attendeeEventList/{userID}")
    public AttendeeEventList getEventList(@PathVariable String userID) {
        LOGGER.info("Request: Getting AttendeeEventList");
        return attendanceService.getEventList(userID);
    }
}