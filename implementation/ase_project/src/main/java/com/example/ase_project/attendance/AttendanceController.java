package com.example.ase_project.attendance;

import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(path = "api/v1")
public class AttendanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PostMapping(value = "/attend/{eventID}")
    public int attend(@PathVariable Long eventID) {
        LOGGER.info("Request: Attending event");
        return AttendanceService.attend(eventID);
    }
    @GetMapping ("/attendance/{eventID}")
    public int attendance(@PathVariable Long eventID) {
        LOGGER.info("Request: Getting attendance count");
        return AttendanceService.attendance(eventID);
    }
}