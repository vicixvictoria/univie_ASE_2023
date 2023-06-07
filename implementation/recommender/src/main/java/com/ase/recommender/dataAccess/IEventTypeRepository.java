package com.ase.recommender.dataAccess;

import com.ase.recommender.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository interface for EventType entities.
 */
@Repository
public interface IEventTypeRepository extends JpaRepository<EventType, String> {

    /**
     * Retrieves the EventType entity by the specified event ID.
     *
     * @param eventID The ID of the event.
     * @return The EventType entity associated with the event ID.
     */
    EventType getByEventID(String eventID);
}
