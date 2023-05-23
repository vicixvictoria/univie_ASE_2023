package com.ase.notification.model.repository;

import com.ase.notification.model.data.RawEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<RawEvent, String> {

}
