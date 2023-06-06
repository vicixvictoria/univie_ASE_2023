package com.ase.recommender.data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.ase.common.event.EEventType;

/**
 * The UserInterest class represents the user's interests in different event types.
 */
@Entity
public class UserInterest {

    @Id
    private String userID;
    private int eventTypeOneCount;
    private int eventTypeTwoCount;
    private int eventTypeThreeCount;

    /**
     * Default constructor for the UserInterest class.
     */
    public UserInterest() {
    }

    /**
     * Constructs a new instance of UserInterest with the specified user ID.
     *
     * @param userID The ID of the user.
     */
    public UserInterest(String userID) {
        this.userID = userID;
    }

    /**
     * Retrieves the ID of the user.
     *
     * @return The ID of the user.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Adds interest in the specified event type.
     *
     * @param eventType The event type to add interest in.
     */
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

    /**
     * Removes interest in the specified event type.
     *
     * @param eventType The event type to remove interest in.
     */
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

    /**
     * Retrieves the interest count for the specified event type.
     *
     * @param eventType The event type to retrieve the interest count for.
     * @return The interest count for the specified event type.
     */
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
