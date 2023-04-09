package com.example.ase_project.Event;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {
    List<Event> findAll();

    List<Event> findAllByorganizerID(Long organizerID);

    Event findEventByeventID(Long id);

    List<Event> findByType(EEventTypes type);

    List<Event> findByCapacity(int capacity);

    List<Event> findByDate(Date date);


}
