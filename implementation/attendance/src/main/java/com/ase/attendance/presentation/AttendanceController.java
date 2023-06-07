package com.ase.attendance.presentation;

import com.ase.attendance.business.AttendanceService;
import com.ase.common.attendance.AttendeeEventList;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/attendance")
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
    public ResponseEntity<Integer> register(@PathVariable String userID, @PathVariable String eventID) {
        LOGGER.info("Request: Register for an event");
        int result = attendanceService.register(userID, eventID);
        return result > 0 ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Deregister a user for an event.
     *
     * @param userID  the ID of the user attending the event.
     * @param eventID the ID of the event being attended.
     * @return the new attendance count for the event.
     */
    @PostMapping(value = "/deregister/{userID}/{eventID}")
    public ResponseEntity<Integer> deregister(@PathVariable String userID, @PathVariable String eventID) {
        LOGGER.info("Request: Deregister from an event");
        int result = attendanceService.deregister(userID, eventID);
        return result >= 0 ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Returns the attendance count for a given event.
     *
     * @param eventID the ID of the event.
     * @return the attendance count for the event.
     */
    @GetMapping("/{eventID}")
    public ResponseEntity<Integer> attendance(@PathVariable String eventID) {
        LOGGER.info("Request: Getting attendance count");
        int result = attendanceService.attendance(eventID);
        return result >= 0 ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Returns a list of events that a given user is attending.
     *
     * @param userID the ID of the user.
     * @return the AttendeeEventList for the user.
     */
    @GetMapping ("/attendeeEventList/{userID}")
    public ResponseEntity<AttendeeEventList> getEventList(@PathVariable String userID) {
        LOGGER.info("Request: Getting AttendeeEventList");
        AttendeeEventList result = attendanceService.getEventList(userID);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Endpoint for performing a health check.
     *
     * @return ResponseEntity with the status and body message.
     */
    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}