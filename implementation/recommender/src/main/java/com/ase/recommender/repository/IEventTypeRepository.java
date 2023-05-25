package com.ase.recommender.repository;

import com.ase.recommender.data.EventType;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ase.common.event.*;

@Repository
public interface IEventTypeRepository extends JpaRepository<EventType, String> {
    EventType getByEventID(String eventID);
}
