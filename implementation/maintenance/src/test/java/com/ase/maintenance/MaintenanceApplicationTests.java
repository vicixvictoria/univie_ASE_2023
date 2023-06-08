package com.ase.maintenance;

import com.ase.maintenance.business.MaintenanceService;
import com.ase.maintenance.business.Availability;
import java.util.ArrayList;

import com.ase.maintenance.integration.RestAccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

class MaintenanceApplicationTests {
    RestAccess restAccess;
    MaintenanceService maintenanceService;

    @BeforeEach
    public void setup() {
        ArrayList<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability("hostname", 200));
        restAccess = Mockito.mock(RestAccess.class);
        Mockito.when(restAccess.getAllAvailabilities()).thenReturn(availabilities);
        maintenanceService = new MaintenanceService(restAccess);

    }
    @Test
    public void maintenanceService_getAvailabiliy_validCall() {
        ResponseEntity<ArrayList<Availability>> response = maintenanceService.getAvailability();
        Assertions.assertNotNull(response);
        for (Availability elem: response.getBody()) {
            Assertions.assertNotNull(elem);
        }
    }
}
