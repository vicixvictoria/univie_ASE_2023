package com.example.ase_project.maintenance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class MaintenanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    public MaintenanceService() {

    }

    /**
     * this method fetchs the logs of all services and returns them
     * @return RseponseEntity<Collection> containing the logs
     */
    public ResponseEntity<String> getLogs() {
        LOGGER.debug("getLogs");
        return new ResponseEntity<>("warn: This is a mock log", HttpStatus.OK);
    }
}
