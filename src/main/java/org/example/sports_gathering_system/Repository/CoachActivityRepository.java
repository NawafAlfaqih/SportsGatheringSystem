package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.CoachActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachActivityRepository extends JpaRepository<CoachActivity, Integer> {

    CoachActivity findCoachActivityById(Integer id);
}
