package com.example.ase_project.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IEventRepository extends JpaRepository<Event, String> {
    List<Event> findAll();

    List<Event> findAllByorganizerID(String organizerID);

    Event findEventByeventID(String id);

    List<Event> findByType(EEventTypes type);

    List<Event> findByCapacity(int capacity);

    List<Event> findByDate(Date date);


}
