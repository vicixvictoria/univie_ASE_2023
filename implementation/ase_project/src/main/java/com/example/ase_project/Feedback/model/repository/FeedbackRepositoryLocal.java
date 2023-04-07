package com.example.ase_project.Feedback.model.repository;

import com.example.ase_project.Feedback.model.data.FeedbackContent;
import com.example.ase_project.Feedback.model.data.FeedbackEvent;
import com.example.ase_project.Feedback.model.data.FeedbackUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Qualifier("local")
public class FeedbackRepositoryLocal implements IFeedbackRepository{
    private final Map<String, FeedbackUser> userStore = new HashMap<>();
    private final Map<String, FeedbackEvent> eventStore = new HashMap<>();
    private final Map<FeedbackUser, Set<FeedbackContent>> userFeedbackStore = new HashMap<>();
    private final Map<FeedbackEvent, Set<FeedbackContent>> eventFeedbackStore = new HashMap<>(); // eventID, feedbacks
    @Override
    public Collection<FeedbackContent> getFeedbackForUser(String userID) {
        this.addUserIfNotInStorage(userID);
        return this.userFeedbackStore.get(this.userStore.get(userID));
    }

    @Override
    public Collection<FeedbackContent> getFeedbackForEvent(String eventID) {
        this.addEventIfNotInStorage(eventID);
        return this.eventFeedbackStore.get(this.eventStore.get(eventID));
    }

    @Override
    public void addFeedback(String userID, String eventID, FeedbackContent feedbackContent) throws IllegalArgumentException{
        if (!this.checkValidityFeedbackContent(feedbackContent)) {
            throw new IllegalArgumentException("feedback can not be null");
        }
        if (!this.checkValidityUserID(userID)) {
            throw new IllegalArgumentException("userID can not be null");
        }
        if (!this.checkValidityEventID(eventID)) {
            throw new IllegalArgumentException("eventID can not be null");
        }

        this.addUserIfNotInStorage(userID);
        this.addEventIfNotInStorage(eventID);

        this.userFeedbackStore.get(userStore.get(userID)).add(feedbackContent);
        this.eventFeedbackStore.get(eventStore.get(eventID)).add(feedbackContent);
    }

    private void addUserIfNotInStorage(String userID){
        if (!userStore.containsKey(userID)) {
            FeedbackUser user = new FeedbackUser(userID);
            userStore.put(userID, user);
            this.userFeedbackStore.put(user, new HashSet<>());
        }
    }
    private void addEventIfNotInStorage(String eventID){
        if (!eventStore.containsKey(eventID)) {
            FeedbackEvent event = new FeedbackEvent(eventID);
            eventStore.put(eventID, event);
            this.eventFeedbackStore.put(event, new HashSet<>());
        }
    }

    private boolean checkValidityUserID(String userID) {
        return (userID != null);
    }

    private boolean checkValidityEventID(String eventID) {
        return (eventID != null);
    }

    private boolean checkValidityFeedbackContent(FeedbackContent content) {
        return (content != null);
    }
}
