package com.ase.attendance.repository;

import com.ase.attendance.data.Attendees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAttendanceRepository extends JpaRepository<Attendees, String> {
    Attendees getByEventID(String eventID);

}
