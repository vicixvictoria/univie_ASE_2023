package com.ase.searchServiceEvents.repository;

import com.ase.searchServiceEvents.domain.EEventTypes;
import com.ase.searchServiceEvents.domain.Event;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISearchServiceRepository extends JpaRepository<Event, String> {

        List<Event> findAll();

        List<Event> findAllByorganizerID(String organizerID);

        Event findEventByeventID(String id);

        List<Event> findByType(EEventTypes type);

        List<Event> findByCapacity(int capacity);

        List<Event> findByDate(Date date);

        List<Event> findByDescription(String des);

        List<Event> findByEventName(String name);

}

