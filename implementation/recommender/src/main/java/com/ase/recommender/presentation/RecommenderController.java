package com.ase.recommender.presentation;

import com.ase.recommender.business.RecommenderService;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class RecommenderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final RecommenderService recommenderService;

    /**
     * Constructs a new instance of AttendanceController.
     *
     * @param recommenderService the AttendanceService to be used by this controller.
     */
    @Autowired
    public RecommenderController(RecommenderService recommenderService) {
        this.recommenderService = recommenderService;
    }

    /**
     * Endpoint for performing a health check.
     *
     * @return ResponseEntity with the status and body message.
     */
    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthcheck(){
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}