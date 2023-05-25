package com.ase.recommender;

import com.ase.common.event.EEventType;
import com.ase.recommender.data.UserInterest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class RecommenderServiceTest {

    private RecommenderFilter recommenderFilter;
    private List<UserInterest> userInterestList;

    @BeforeEach
    public void setup() {
        this.recommenderFilter = new RecommenderFilter(3);

        UserInterest user1 = new UserInterest("user1");
        for (int i = 0; i < 2; i++) {
            user1.addInterest(EEventType.FOOD);
        }
        for (int i = 0; i < 5; i++) {
            user1.addInterest(EEventType.ENTERTAINMENT);
        }

        UserInterest user2 = new UserInterest("user2");
        for (int i = 0; i < 4; i++) {
            user2.addInterest(EEventType.FOOD);
        }

        this.userInterestList = new ArrayList<>();
        this.userInterestList.add(user1);
        this.userInterestList.add(user2);
    }

    @Test
    public void testFilter() {
        List<String> result = recommenderFilter.filter(userInterestList, EEventType.FOOD);
        assertEquals(1, result.size());
        assertTrue(result.contains("user2"));

        result = recommenderFilter.filter(userInterestList, EEventType.ENTERTAINMENT);
        assertEquals(1, result.size());
        assertTrue(result.contains("user1"));
    }
}
