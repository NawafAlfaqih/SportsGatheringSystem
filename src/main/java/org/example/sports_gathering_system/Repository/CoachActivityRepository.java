package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.CoachActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachActivityRepository extends JpaRepository<CoachActivity, Integer> {

    CoachActivity findCoachActivityById(Integer id);

    List<CoachActivity> findCoachActivitiesByCoachId(Integer coachId);

    List<CoachActivity> findCoachActivitiesBySportId(Integer sportId);

    @Query("select c from CoachActivity c order by c.dateTime asc")
    List<CoachActivity> sortAsc();

    @Query("select c from CoachActivity c order by c.dateTime desc")
    List<CoachActivity> sortDesc();
}
