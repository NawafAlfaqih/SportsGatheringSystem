package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.CoachJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachJoinRequestRepository extends JpaRepository<CoachJoinRequest, Integer> {

    CoachJoinRequest findCoachJoinRequestById(Integer id);
}
