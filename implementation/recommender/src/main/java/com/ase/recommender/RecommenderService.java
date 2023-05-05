package com.ase.recommender;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RecommenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    /**
     * This method is called for getting a List of eventID recommendations for a given attendeeID
     *
     * @param attendeeID for attendee identification
     * @return List of recommended eventIDs
     */
    public static List<String> recommend(String attendeeID) {
        List<String> recommendations = new ArrayList<>();
        recommendations.add("100");
        recommendations.add("200");
        recommendations.add("300");
        LOGGER.info("Mock recommendations created for attendeeID: " + attendeeID);
        return recommendations;
    }
}
