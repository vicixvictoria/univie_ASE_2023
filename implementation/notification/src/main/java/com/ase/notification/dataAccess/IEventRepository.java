package com.ase.notification.dataAccess;

import com.ase.notification.domain.RawEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<RawEvent, String> {

}
