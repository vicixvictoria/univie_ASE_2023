package com.example.ase_project.analyticReport;

import com.example.ase_project.analyticReport.model.AnalyticReportService;
import com.example.ase_project.analyticReport.model.data.AnalyticReportAttendee;
import com.example.ase_project.analyticReport.model.data.AnalyticReportOrganizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
public class AnalyticReportController {

    private final AnalyticReportService analyticReportService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public AnalyticReportController(AnalyticReportService reportService) {
        this.analyticReportService = reportService;
    }

    @GetMapping(value = "/getAnalyticReportAttendee/{eventID}")
    public ResponseEntity<AnalyticReportAttendee> getAnalyticReportAttendee(@PathVariable String eventID) {
        LOGGER.info("GET api/v1/analyticReportAttendee/{}", eventID);
        return analyticReportService.getAnalyticReportAttendee(eventID);
    }

    @GetMapping(value = "/getAnalyticReportOrganizer/{eventID}")
    public ResponseEntity<AnalyticReportOrganizer> getAnalyticReportOrganizer(@PathVariable String eventID) {
        LOGGER.info("GET api/v1/analyticReportOrganizer/{}", eventID);
        return analyticReportService.getAnalyticReportOrganizer(eventID);
    }
}
