package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach,Integer> {

    Coach findCoachById(Integer id);
}
