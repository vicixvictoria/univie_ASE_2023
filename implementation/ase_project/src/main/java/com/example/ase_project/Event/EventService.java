package com.example.ase_project.Event;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    private final IEventRepository iEventRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public EventService(IEventRepository iEventRepository) {
        this.iEventRepository = iEventRepository;
    }

    /**
     * This method is called to get a List of all saved events
     *
     * @return List of events
     */
    public List<Event> getAll() {
        LOGGER.debug("getAll Events");
        return iEventRepository.findAll();
    }

    /**
     * This method is called to get a List of all events from one organizer based on their id
     *
     * @param id of organizer
     * @return List of all events from one organizer
     */
    public List<Event> getAllEventsByorganizerID(Long id) {
        LOGGER.debug("getAll Events by organizerID {}", id);
        return iEventRepository.findAllByorganizerID(id);
    }

    /**
     * This method is called to get a specific Event by their id
     *
     * @param id from one event
     * @return event wiht id
     */
    public Event getEventsByID(Long id) {
        LOGGER.debug("getAll Events by eventID {}", id);
        return iEventRepository.findEventByeventID(id);
    }

    /**
     * This method is called to get a List of all events with the same date
     *
     * @param date
     * @return List of events with the same date
     */
    public List<Event> getEventByDate(Date date) {
        LOGGER.debug("getAll Events by Date {}", date);
        return iEventRepository.findByDate(date);
    }

    /**
     * This method is called to get a List of all events with the same capacity
     *
     * @param capacity
     * @return List of events with the same capacity
     */
    public List<Event> getEventByCapacity(int capacity) {
        LOGGER.debug("getAll Events by capacity {}", capacity);
        return iEventRepository.findByCapacity(capacity);
    }

    /**
     * This method is called to get a List of all events with the same type
     *
     * @param type
     * @return List of events with the same type
     */
    public List<Event> getEventByType(EEventTypes type) {
        LOGGER.debug("getAll Events by type {}", type);
        return iEventRepository.findByType(type);
    }

    /**
     * This methode is called to create and save a new event with the given parameters in the object event
     *
     * @param event to be inserted
     * @return newly created and saved event
     */
    @Transactional
    public Event createEvent(Event event) {
        LOGGER.debug("create and save new event {}", event);
        return iEventRepository.save(event);
    }

    /**
     * This method is called to update and existing event with the given parameters in the object event
     *
     * @param event to be inserted
     * @return updated event
     */
    @Transactional
    public Event updateEvent(Event event) {
        LOGGER.debug("update event {}", event);
        Event event1 = iEventRepository.findEventByeventID(event.getEventID());
        return iEventRepository.save(event);
    }

}
