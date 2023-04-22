package com.example.ase_project.attendance;

import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @PostMapping(value = "/attend/{eventID}")
    public int attend(@PathVariable String eventID) {
        LOGGER.info("Request: Attending event");
        return AttendanceService.attend(eventID);
    }

    @GetMapping("/attendance/{eventID}")
    public int attendance(@PathVariable String eventID) {
        LOGGER.info("Request: Getting attendance count");
        return AttendanceService.attendance(eventID);
    }
}