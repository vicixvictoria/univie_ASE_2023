package com.ase.searchServiceEvents.Service;
import com.ase.searchServiceEvents.Repository.ISearchServiceRepository;
import com.ase.searchServiceEvents.event.EEventTypes;
import com.ase.searchServiceEvents.event.Event;
import jakarta.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SearchServiceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final ISearchServiceRepository iSearchServiceRepository;

    @Autowired
    public SearchServiceService(ISearchServiceRepository iSearchServiceRepository) {
        this.iSearchServiceRepository = iSearchServiceRepository;
    }

    /**
     * This method is called to get a List of all saved events
     *
     * @return List of events
     */
    public List<Event> getAll() {
        LOGGER.debug("getAll Events");
        return iSearchServiceRepository.findAll();
    }

    /**
     * This method is called to get a List of all events from one organizer based on their id
     *
     * @param id of organizer
     * @return List of all events from one organizer
     */
    public List<Event> getAllEventsByorganizerID(String id) {
        LOGGER.debug("getAll Events by organizerID {}", id);
        if (id == null){
            LOGGER.error("OrganizerID is empty");
            throw new RuntimeException("OrganizerID is not correct");
        }
        return iSearchServiceRepository.findAllByorganizerID(id);
    }

    /**
     * This method is called to get a specific Event by their id
     *
     * @param id from one event
     * @return event wiht id
     */
    public Event getEventsByID(String id) {
        LOGGER.debug("getAll Events by eventID {}", id);
        if (id == null){
            LOGGER.error("ID is empty");
            throw new RuntimeException("ID is not correct");
        }
        return iSearchServiceRepository.findEventByeventID(id);
    }

    /**
     * This method is called to get a List of all events with the same date
     *
     * @param date
     * @return List of events with the same date
     */
    public List<Event> getEventsByDate(Date date) {
        LOGGER.debug("getAll Events by Date {}", date);
        if (date == null){
            LOGGER.error("Date is empty");
            throw new RuntimeException("Date is not correct");
        }
        return iSearchServiceRepository.findByDate(date);
    }

    /**
     * This method is called to get a List of all events with the same capacity
     *
     * @param capacity
     * @return List of events with the same capacity
     */
    public List<Event> getEventsByCapacity(int capacity) {
        LOGGER.debug("getAll Events by capacity {}", capacity);
        return iSearchServiceRepository.findByCapacity(capacity);
    }

    /**
     * This method is called to get a List of all events with the same capacity
     *
     * @param name
     * @return List of events with the same capacity
     */
    public List<Event> getEventsByName(String name) {
        LOGGER.debug("getAll Events by name {}", name);
        if (name == null){
            LOGGER.error("Name is empty");
            throw new RuntimeException("Name is not correct");
        }
        return iSearchServiceRepository.findByEventName(name);
    }

    /**
     * This method is called to get a List of all events with the same type
     *
     * @param type
     * @return List of events with the same type
     */
    public List<Event> getEventsByType(EEventTypes type) {
        LOGGER.debug("getAll Events by type {}", type);
        if (type == null){
            LOGGER.error("Type is empty");
            throw new RuntimeException("Type is not correct");
        }
        return iSearchServiceRepository.findByType(type);
    }

    /**
     * This method is called to get a List of all events with the same description
     *
     * @param des
     * @return List of events with the same type
     */
    public List<Event> getEventsByDescription(String des) {
        LOGGER.debug("getAll Events by description {}", des);
        if (des == null){
            LOGGER.error("Description is empty");
            throw new RuntimeException("Description is not correct");
        }
        return iSearchServiceRepository.findByDescription(des);
    }

    /**
     * This methode is called to create and save a new event with the given parameters in the object
     * event
     *
     * @param event to be inserted
     * @return newly created and saved event
     */
    @Transactional
    public Event createEvent(Event event) {
        LOGGER.debug("create and save new event {}", event);
        //eventValidator.validateNewEvent(event);
        if (event == null || event.getEventName() == null || event.getCapacity()<1){
            LOGGER.error("Event is not correct. Either its empty, or it has no name, or the Capacity is below 1, or there is no date.");
            throw new RuntimeException("Event not correct");
        }
        Event newEvent =  iSearchServiceRepository.save(event);
        return newEvent;
    }

    /**
     * This method is called to update and existing event with the given parameters in the object
     * event
     *
     * @param event to be inserted
     * @return updated event
     */
    @Transactional
    public Event updateEvent(Event event) {
        LOGGER.debug("update event {}", event);
        if (event == null || event.getEventName() == null || event.getCapacity()<1|| event.getDate() == null){
            LOGGER.error("Event is not correct. Either its empty, or it has no name, or the Capacity is below 1, or there is no date.");
            throw new RuntimeException("Event not correct");
        }
        Event event1 = iSearchServiceRepository.findEventByeventID(event.getEventID());
        Event updatedEvent = iSearchServiceRepository.save(event);
        return updatedEvent;
    }

    /**
     * This method is called to delete an event with a certain ID
     *
     * @param eventID
     */
    public void deleteEvent(String eventID) {
        LOGGER.debug("delete event {}", getEventsByID(eventID));
        if(eventID == null){
            LOGGER.error("EventID is empty");
            throw new RuntimeException("EventID not correct");
        }
        Event deletedEvent = iSearchServiceRepository.findEventByeventID(eventID);
        iSearchServiceRepository.deleteById(eventID);
    }


}
