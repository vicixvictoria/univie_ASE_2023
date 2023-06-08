package com.ase.bookmarkService.repository;

import com.ase.bookmarkService.BookmarkEvent;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookmarkRepository extends JpaRepository<BookmarkEvent, String> {

    /**
     * @param attendeeID
     * @return all bookmarked events as EventId for attendeeID
     */
    List<BookmarkEvent> getAllByAttendeeID(String attendeeID);

    /**
     * deletes event based on attendeeId and eventId
     *
     * @param attendeeID, eventID
     */
    @Transactional
    void deleteBookmarkEventsByAttendeeIDAndEventID(String attendeeID, String eventID);
}