package com.ase.maintenance.business;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import com.ase.maintenance.integration.RestAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class MaintenanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final RestAccess restAccess;


    @Autowired
    public MaintenanceService(RestAccess restAccess) {
        this.restAccess = restAccess;
    }

    /**
     * this method access the /healthcheck endpoint for all services and returns a list of the results.
     * @return ArrayList<Availability>
     */
    public ResponseEntity<ArrayList<Availability>> getAvailability() {

        return new ResponseEntity<>(restAccess.getAllAvailabilities(), HttpStatus.OK);
    }
}
