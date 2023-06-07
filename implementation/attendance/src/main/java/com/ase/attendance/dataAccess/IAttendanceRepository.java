package com.ase.attendance.dataAccess;

import com.ase.attendance.domain.Attendees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The IAttendanceRepository interface is a repository for managing Attendees entities.
 */
@Repository
public interface IAttendanceRepository extends JpaRepository<Attendees, String> {

    /**
     * Retrieves the Attendees entity based on the event ID.
     *
     * @param eventID The ID of the event.
     * @return The Attendees entity associated with the event ID.
     */
    Attendees getByEventID(String eventID);
}
