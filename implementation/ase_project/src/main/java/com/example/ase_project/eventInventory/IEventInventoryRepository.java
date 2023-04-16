package com.example.ase_project.eventInventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventInventoryRepository extends JpaRepository<EventInventory, String> {

    List<EventInventory> findAll();

    EventInventory findByorganizerID(String id);
}


