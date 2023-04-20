package com.example.ase_project.eventInventory;

import com.example.ase_project.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class EventInventoryService {

    private final IEventInventoryRepository iEventInventoryRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public EventInventoryService(IEventInventoryRepository iEventInventoryRepository) {
        this.iEventInventoryRepository = iEventInventoryRepository;
    }

    /**
     * This method is called T to get a List of all saved event inventories
     *
     * @return List of event inventories
     */
    public List<EventInventory> getAll() {
        LOGGER.debug("getAll Event Inventories");
        return iEventInventoryRepository.findAll();
    }

    /**
     * This method is called to get the event inventory from one organizer based on their id
     *
     * @param id of organizer
     * @return event inventory
     */
    public EventInventory getEventInventoryByOrganizerID(String id) {
        LOGGER.debug("get Event Inventory by organizerID {}", id);
        return iEventInventoryRepository.findByorganizerID(id);
    }

    /**
     * This methode is called to create and save a new event inventory with the given parameters in the object eventInventory
     *
     * @param eventInventory to be inserted
     * @return newly created eventInventory
     */
    public EventInventory createEventInventory(EventInventory eventInventory) {
        LOGGER.debug("create new Event Inventory {}", eventInventory);
        return iEventInventoryRepository.save(eventInventory);
    }

    /**
     * this method is called to update and existing event inventory by the given event
     *
     * @param event should deliver organizerID and eventID so that the right eventListID can be updated
     */
    public void updateEventInventoryEvents(Event event) {
        LOGGER.debug("update existing Event Inventory Event List{}", event);
        String organizerID = event.getOrganizerID();
        getEventInventoryByOrganizerID(organizerID).getEventListID().add(event.getEventID());
    }

    /**
     * this method is called to update and existing event inventory with the given parameters in the object eventInventory
     *
     * @param eventInventory to be inserted
     * @return updated eventInventory
     */
    public EventInventory updateEventInventory(EventInventory eventInventory) {
        LOGGER.debug("update existing Event Inventory {}", eventInventory);
        return iEventInventoryRepository.save(eventInventory);
    }

    /**
     * This method is called to delete an eventInventory with a certain ID
     * @param eventInventoryID
     */
    public void deleteEvent(String eventInventoryID){
        LOGGER.debug("delete event {}", eventInventoryID);
        iEventInventoryRepository.deleteById(eventInventoryID);
    }

}
