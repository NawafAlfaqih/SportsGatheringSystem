package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.UserParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserParticipantRepository extends JpaRepository<UserParticipant, Integer> {

    UserParticipant findUserParticipantById(Integer id);

    List<UserParticipant> findUserParticipantsByActivityId(Integer activityId);

}
