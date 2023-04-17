package com.example.ase_project.recommender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommenderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static List<Long> recommend(Long attendeeID) {
        List<Long> recommendations = new ArrayList<>();
        recommendations.add((long)100);
        recommendations.add((long)200);
        recommendations.add((long)300);
        LOGGER.info("Mock recommendations created for attendeeID: " + attendeeID);
        return recommendations;
    }
}
