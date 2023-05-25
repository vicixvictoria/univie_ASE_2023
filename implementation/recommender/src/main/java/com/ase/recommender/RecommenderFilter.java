package com.ase.recommender;

import com.ase.common.event.EEventType;
import com.ase.recommender.data.UserInterest;
import java.util.ArrayList;
import java.util.List;

public class RecommenderFilter {

    int interestThreshold;

    public RecommenderFilter(int interestThreshold) {
        this.interestThreshold = interestThreshold;
    }

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
