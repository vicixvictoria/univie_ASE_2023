package com.ase.recommender.business;

import com.ase.common.event.EEventType;
import com.ase.common.user.User;
import com.ase.recommender.domain.RecommenderFilter;
import com.ase.recommender.domain.EventType;
import com.ase.recommender.domain.UserInterest;
import com.ase.recommender.integration.Publisher;
import com.ase.recommender.dataAccess.IEventTypeRepository;
import com.ase.recommender.dataAccess.IRecommenderRepository;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The RecommenderService class provides methods for event recommendations based on user interests.
 */
@Service
public class RecommenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final IRecommenderRepository recommenderRepository;
    private final IEventTypeRepository eventTypeRepository;
    private final Publisher publisher;
    private final RecommenderFilter userFilter;

    /**
     * Constructs a new instance of RecommenderService.
     *
     * @param recommenderRepository The repository for user interests.
     * @param eventTypeRepository   The repository for event types.
     * @param publisher             The publisher for event recommendations.
     */
    @Autowired
    public RecommenderService(IRecommenderRepository recommenderRepository, IEventTypeRepository eventTypeRepository, Publisher publisher) {
        this.recommenderRepository = recommenderRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.publisher = publisher;
        userFilter = new RecommenderFilter(3);

    }

    /**
     * Generates a list of event ID recommendations for a given attendee ID based on the event type.
     *
     * @param eventType The event type for attendee identification.
     */
     public void recommend(String eventID, EEventType eventType) {
        List<UserInterest> interestList = recommenderRepository.findAll();
        publisher.recommend(eventID, userFilter.filter(interestList, eventType));
    }

    /**
     * Adds a new event type to the repository and triggers recommendations for the event type.
     *
     * @param eventID   The ID of the event.
     * @param eventType The event type.
     */
    public void addEventType(String eventID, EEventType eventType) {
            eventTypeRepository.save(new EventType(eventID, eventType));
            recommend(eventID, eventType);
    }

    /**
     * Updates the event type of existing event and triggers recommendations for the updated event type.
     *
     * @param eventID   The ID of the event.
     * @param eventType The updated event type.
     */
    public void updateEventType(String eventID, EEventType eventType) {
        eventTypeRepository.getByEventID(eventID).setEventType(eventType);
    }

    /**
     * Removes an event type from the repository.
     *
     * @param eventID   The ID of the event.
     * @param eventType The event type to remove.
     */
    public void removeEventType(String eventID, EEventType eventType) {
        eventTypeRepository.save(new EventType(eventID, eventType));
    }

    /**
     * Adds user interest for a specific event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user.
     * @return true if the interest was added successfully, false otherwise.
     */
    public boolean addInterest(String userID, String eventID) {
        if(eventTypeRepository.existsById(eventID)) {
            EventType eventTypeObject = eventTypeRepository.getByEventID(eventID);
            if (eventTypeObject != null) {
                EEventType eventType = eventTypeObject.getEventType();
                if (eventType != null) {
                    if (!recommenderRepository.existsById(userID)) {
                        recommenderRepository.save(new UserInterest(userID));
                    }
                    UserInterest userInterest = recommenderRepository.getByUserID(userID);
                    userInterest.addInterest(eventType);
                    recommenderRepository.save(userInterest);
                    return true;
                } else {
                    LOGGER.error("EventType inside EventType object is null");
                    return false;
                }
            } else {
                LOGGER.error("EventType object is null");
                return false;
            }
        }
        return false;
    }


    /**
     * Removes user interest for a specific event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user.
     * @return true if the interest was removed successfully, false otherwise.
     */
    public boolean removeInterest(String eventID, String userID) {
        if(eventTypeRepository.existsById(eventID)) {
            EventType eventTypeObject = eventTypeRepository.getByEventID(eventID);
            if (eventTypeObject != null) {
                EEventType eventType = eventTypeObject.getEventType();
                if (eventType != null) {
                    if(recommenderRepository.existsById(userID)) {
                        UserInterest userInterest = recommenderRepository.getByUserID(userID);
                        if(userInterest != null) {
                            userInterest.removeInterest(eventType);
                            return true;
                        } else {
                            LOGGER.error("UserInterest object is null");
                            return false;
                        }
                    } else {
                        LOGGER.error("User does not exist");
                        return false;
                    }
                } else {
                    LOGGER.error("EventType inside EventType object is null");
                    return false;
                }
            } else {
                LOGGER.error("EventType object is null");
                return false;
            }
        }
        return false;
    }
}
