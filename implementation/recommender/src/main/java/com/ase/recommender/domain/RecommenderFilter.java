package com.ase.recommender.domain;

import com.ase.common.event.EEventType;
import com.ase.recommender.domain.UserInterest;
import java.util.ArrayList;
import java.util.List;

/**
 * The RecommenderFilter class filters user interests based on a threshold value.
 */
public class RecommenderFilter {

    /**
     * The threshold value for filtering user interests.
     */
    int interestThreshold;

    /**
     * Constructs a new instance of RecommenderFilter with the specified interest threshold.
     *
     * @param interestThreshold The threshold value for filtering user interests.
     */
    public RecommenderFilter(int interestThreshold) {
        this.interestThreshold = interestThreshold;
    }

    /**
     * Filters the user interests based on the specified event type and interest threshold.
     *
     * @param userInterestList The list of user interests.
     * @param eventType        The event type to filter.
     * @return The list of user IDs that meet the interest threshold for the specified event type.
     */
    public List<String> filter(List<UserInterest> userInterestList, EEventType eventType) {
        List<String> userRecommendationList = new ArrayList<>();
        for (int i = 0; i < userInterestList.size(); i++) {
            if(userInterestList.get(i).getInterest(eventType) >= interestThreshold) {
                userRecommendationList.add(userInterestList.get(i).getUserID());
            }
        }
        return userRecommendationList;
    }
}
