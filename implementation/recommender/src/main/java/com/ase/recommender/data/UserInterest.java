package com.ase.recommender.data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.ase.common.event.EEventType;

@Entity
public class UserInterest {
    @Id
    private String userID;

    private int eventTypeOneCount;

    private int eventTypeTwoCount;

    private int eventTypeThreeCount;

    public UserInterest() {
    }

    public UserInterest(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void addInterest(EEventType eventType) {
        if (eventType.name().equals("FOOD")) {
            eventTypeOneCount++;
        }
        if (eventType.name().equals("ENTERTAINMENT")) {
            eventTypeTwoCount++;
        }
        if (eventType.name().equals("HEALTH")) {
            eventTypeThreeCount++;
        }
    }

    public void removeInterest(EEventType eventType) {
        if (eventType.name().equals("FOOD")) {
            eventTypeOneCount--;
        }
        if (eventType.name().equals("ENTERTAINMENT")) {
            eventTypeTwoCount--;
        }
        if (eventType.name().equals("HEALTH")) {
            eventTypeThreeCount--;
        }
    }

    public int getInterest(EEventType eventType) {
        if (eventType.name().equals("FOOD")) {
            return eventTypeOneCount;
        }
        if (eventType.name().equals("ENTERTAINMENT")) {
            return eventTypeTwoCount;
        }
        if (eventType.name().equals("HEALTH")) {
            return eventTypeThreeCount;
        }
        return 0;
    }
}
