package com.example.ase_project.recommender;

import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(path = "/recommend")
public class RecommenderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping("/{attendeeID}")
    public List<Long> recommend(@PathVariable Long attendeeID) {
        LOGGER.info("Mock recommendations send");
        return RecommenderService.recommend(attendeeID);
    }
}