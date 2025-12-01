package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.CoachParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachParticipantRepository extends JpaRepository<CoachParticipant, Integer> {

    CoachParticipant findCoachParticipantById(Integer id);
}
