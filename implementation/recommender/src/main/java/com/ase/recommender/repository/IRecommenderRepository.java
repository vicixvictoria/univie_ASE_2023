package com.ase.recommender.repository;

import com.ase.recommender.data.UserInterest;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ase.common.event.*;

@Repository
public interface IRecommenderRepository extends JpaRepository<UserInterest, String> {
    UserInterest getByUserID(String userID);

}
