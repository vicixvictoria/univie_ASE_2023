package com.ase.feedback.model.repository;

import com.ase.feedback.model.data.Feedback;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback, String> {

    Collection<Feedback> getByEventID(String eventID);

    Collection<Feedback> getByUserID(String userID);

    Feedback getByFeedbackID(String feedbackID);

    List<Feedback> findAll();

}
