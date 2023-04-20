package com.example.ase_project.maintenance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public class MaintenanceServiceTests {
    MaintenanceService maintenanceService = new MaintenanceService();

    @Test
    public void maintenanceServic_getLogs_validCall() {
        ResponseEntity<String> response = maintenanceService.getLogs();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }
}
