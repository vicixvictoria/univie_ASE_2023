package com.ase.taggingService.repository;

import com.ase.taggingService.TaggingEvent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaggingRepository extends JpaRepository<TaggingEvent, String> {

    boolean existsByEventIdAndUserId(String eventId, String userId);

    @Transactional
    void deleteByEventIdAndUserId(String eventId, String userId);

    @Transactional
    TaggingEvent getTaggingEventByEventIdAndUserId(String eventId, String userId);

    @Transactional
    void deleteAllByEventIdAndUserId(String eventId, String userId);

}
