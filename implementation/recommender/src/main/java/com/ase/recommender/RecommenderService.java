package com.ase.recommender;

import com.ase.common.event.EEventType;
import com.ase.recommender.data.EventType;
import com.ase.recommender.data.UserInterest;
import com.ase.recommender.network.Publisher;
import com.ase.recommender.repository.IEventTypeRepository;
import com.ase.recommender.repository.IRecommenderRepository;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final IRecommenderRepository recommenderRepository;
    private final IEventTypeRepository eventTypeRepository;
    private final Publisher publisher;

    private final RecommenderFilter userFilter;

    @Autowired
    public RecommenderService(IRecommenderRepository recommenderRepository, IEventTypeRepository eventTypeRepository, Publisher publisher) {
        this.recommenderRepository = recommenderRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.publisher = publisher;
        userFilter = new RecommenderFilter(3);

    }

    /**
     * This method is called for getting a List of eventID recommendations for a given attendeeID
     *
     * @param eventType for attendee identification
     * @return List of recommended eventIDs
     */
    public void recommend(EEventType eventType) {
        List<UserInterest> interestList = recommenderRepository.findAll();
        publisher.recommend(userFilter.filter(interestList, eventType));
    }

    public void addEventType(String eventID, EEventType eventType) {
            eventTypeRepository.save(new EventType(eventID, eventType));
            recommend(eventType);
    }

    public void updateEventType(String eventID, EEventType eventType) {
        eventTypeRepository.getByEventID(eventID).setEventType(eventType);
    }

    public void removeEventType(String eventID, EEventType eventType) {
        eventTypeRepository.save(new EventType(eventID, eventType));
    }

    public boolean addInterest(String eventID, String userID) {
        if(eventTypeRepository.existsById(eventID)) {
            EEventType eventType = eventTypeRepository.getByEventID(eventID).getEventType();
            if(recommenderRepository.existsById(userID)) {
                recommenderRepository.save(new UserInterest(userID));
            }
            recommenderRepository.getByUserID(userID).addInterest(eventType);
            return true;
        }
        return false;
    }

    public boolean removeInterest(String eventID, String userID) {
        if(eventTypeRepository.existsById(eventID)) {
            EEventType eventType = eventTypeRepository.getByEventID(eventID).getEventType();
            if(recommenderRepository.existsById(userID)) {
                return false;
            }
            recommenderRepository.getByUserID(userID).removeInterest(eventType);
            return true;
        }
        return false;
    }
}
