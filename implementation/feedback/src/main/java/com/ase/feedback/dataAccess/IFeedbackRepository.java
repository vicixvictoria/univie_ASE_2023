package com.ase.feedback.dataAccess;

import com.ase.feedback.domain.MyFeedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFeedbackRepository extends JpaRepository<MyFeedback, String> {

    List<MyFeedback> getByEventID(String eventID);

    List<MyFeedback> getByUserID(String userID);

    MyFeedback getByFeedbackID(String feedbackID);

    List<MyFeedback> findAll();

}
