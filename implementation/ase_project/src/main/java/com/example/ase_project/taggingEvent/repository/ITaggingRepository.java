package com.example.ase_project.taggingEvent.repository;

import com.example.ase_project.taggingEvent.TaggingEvent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaggingRepository extends JpaRepository<TaggingEvent, String> {

    boolean existsByEventIdAndUserId(String eventId, String userId);

    @Transactional
    void deleteByEventIdAndUserId(String eventId, String userId);

    TaggingEvent getTaggingEventByEventIdAndUserId(String eventId, String userId);

    @Transactional
    void deleteAllByEventIdAndUserId(String eventId, String userId);

}
