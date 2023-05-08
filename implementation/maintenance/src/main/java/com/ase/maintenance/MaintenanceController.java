package com.ase.maintenance;

import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/maintenance")
public class MaintenanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final MaintenanceService service;

    @Autowired
    public MaintenanceController(MaintenanceService service) {
        this.service = service;
    }

    @GetMapping(value = "/logs")
    public ResponseEntity<String> getLogs() {
        LOGGER.info("GET api/v1/maintenance/logs");
        return service.getLogs();
    }
}
