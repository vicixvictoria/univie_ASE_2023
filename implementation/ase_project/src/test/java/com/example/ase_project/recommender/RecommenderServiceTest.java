package com.example.ase_project.recommender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static com.example.ase_project.recommender.RecommenderService.recommend;

public class RecommenderServiceTest {
    @Test
    public void recommendTest() {
        Assertions.assertEquals(recommend("100"), recommend("100"));
    }
}
