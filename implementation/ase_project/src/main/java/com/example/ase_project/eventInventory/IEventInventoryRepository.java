package com.example.ase_project.eventInventory;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventInventoryRepository extends JpaRepository<EventInventory, String> {

    List<EventInventory> findAll();

    EventInventory findByorganizerID(String id);
}


