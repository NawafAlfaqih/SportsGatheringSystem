package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Integer> {

    UserRating findUserRatingById(Integer id);

    UserRating findUserRatingByRaterIdAndTargetUserIdAndActivityId(Integer raterId, Integer targetUserId, Integer activityId);

    List<UserRating> findUserRatingsByTargetUserId(Integer userId);

    List<UserRating> findUserRatingsByRaterId(Integer userId);

    List<UserRating> findUserRatingsByActivityId(Integer activityId);

    @Query("select avg(r.score) from UserRating r where r.targetUserId = ?1")
    Double getAverageRating(Integer targetId);

}
