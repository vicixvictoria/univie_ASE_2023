package com.ase.maintenance.presentation;

import com.ase.maintenance.business.MaintenanceService;
import com.ase.maintenance.business.Availability;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/maintenance/")
public class MaintenanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final MaintenanceService service;

    @Autowired
    public MaintenanceController(MaintenanceService service) {
        this.service = service;
    }

    @GetMapping(value = "availability")
    public ResponseEntity<ArrayList<Availability>> getAvailability() {
        LOGGER.info("GET api/v1/maintenance/availability");
        return service.getAvailability();
    }
}
