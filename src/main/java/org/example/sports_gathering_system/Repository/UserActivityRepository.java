package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Integer> {

    UserActivity findUserActivityById(Integer id);

    List<UserActivity> getUserActivitiesByDateTime(LocalDateTime dateTime);

    @Query("SELECT a FROM UserActivity a WHERE a.location = ?1")
    List<UserActivity> findActivitiesByCity(String city);

    List<UserActivity> findUserActivitiesBySportId(Integer sportId);

    @Query("SELECT a FROM UserActivity a WHERE a.dateTime >= CURRENT_TIMESTAMP ORDER BY a.dateTime ASC")
    List<UserActivity> findUpcomingActivities();

    @Query("select u from UserActivity u order by u.dateTime asc")
    List<UserActivity> sortAsc();

    @Query("select u from UserActivity u order by u.dateTime desc")
    List<UserActivity> sortDesc();



}
