package com.ase.recommender.repository;

import com.ase.recommender.data.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository interface for UserInterest entities.
 */
@Repository
public interface IRecommenderRepository extends JpaRepository<UserInterest, String> {

    /**
     * Retrieves the UserInterest entity by the specified user ID.
     *
     * @param userID The ID of the user.
     * @return The UserInterest entity associated with the user ID.
     */
    UserInterest getByUserID(String userID);
}
