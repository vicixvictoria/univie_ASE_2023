package com.ase.bookmarkService.repository;

import com.ase.bookmarkService.BookmarkEvent;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookmarkRepository extends JpaRepository<BookmarkEvent, String> {

    List<BookmarkEvent> getAllByAttendeeID(String attendeeID);

    @Transactional
    void deleteBookmarkEventsByAttendeeIDAndEventID(String attendeeID, String eventID);
}