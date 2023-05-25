package com.ase.attendance;

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

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping(value = "/attend/{userID}/{eventID}")
    public int attend(@PathVariable String userID, String eventID) {
        LOGGER.info("Request: Attending event");
        return attendanceService.attend(userID, eventID);
    }

    @GetMapping("/attendance/{eventID}")
    public int attendance(@PathVariable String eventID) {
        LOGGER.info("Request: Getting attendance count");
        return attendanceService.attendance(eventID);
    }
}