package com.example.ase_project.Feedback.model.repository;

import com.example.ase_project.Feedback.model.data.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback, String> {
    Collection<Feedback> getByEventID(String eventID);
    Collection<Feedback> getByUserID(String userID);

    List<Feedback> findAll();

}
