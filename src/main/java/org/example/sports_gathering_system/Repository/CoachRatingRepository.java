package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.CoachRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRatingRepository extends JpaRepository<CoachRating, Integer> {

    CoachRating findCoachRatingById(Integer id);
}
