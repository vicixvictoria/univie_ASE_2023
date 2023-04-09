package com.example.ase_project.EventInventory;

import com.example.ase_project.Event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventInventoryRepository extends JpaRepository<EventInventory, Long> {

    List<EventInventory> findAll();

    EventInventory findByorganizerID(Long id);
}


