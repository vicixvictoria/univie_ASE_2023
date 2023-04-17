package com.example.ase_project.attendance;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;

@RestController
public class AttendanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PostMapping(value = "/attend/{eventID}")
    public int attend(@PathVariable Long eventID) {
        LOGGER.info("Attended event");
        return AttendanceService.attend(eventID);
    }
}