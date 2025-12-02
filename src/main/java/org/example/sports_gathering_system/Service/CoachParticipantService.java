package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Model.CoachActivity;
import org.example.sports_gathering_system.Model.CoachParticipant;
import org.example.sports_gathering_system.Model.User;
import org.example.sports_gathering_system.Repository.CoachActivityRepository;
import org.example.sports_gathering_system.Repository.CoachParticipantRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachParticipantService {

    private final CoachParticipantRepository coachParticipantRepository;
    private final CoachActivityRepository coachActivityRepository;
    private final UserRepository userRepository;

    public List<CoachParticipant> getAllParticipants() {
        return coachParticipantRepository.findAll();
    }

    //used in another service not controller
    public Integer addParticipant(Integer activityId, Integer coachId, Integer participantId) {

        CoachActivity activity = coachActivityRepository.findCoachActivityById(activityId);
        if (activity == null)
            return -1; // activity not found

        User participant = userRepository.findUserById(participantId);
        if (participant == null)
            return -2; // user not found

        if (!participant.getGender().equalsIgnoreCase(activity.getParticipantsGender()))
            return -3; //gender not same

        for (CoachParticipant cp : coachParticipantRepository.findAll()) {
            if (cp.getActivityId().equals(activityId) &&
                    cp.getParticipantId().equals(participantId)) {
                return -4; //already joined
            }
        }
        long count = coachParticipantRepository.findAll().stream()
                .filter(p -> p.getActivityId().equals(activityId))
                .count();
        if (count >= activity.getMaxParticipants())
            return -5; // full

        CoachParticipant newP = new CoachParticipant(null, activityId, coachId, participantId);
        coachParticipantRepository.save(newP);
        return 1;
    }
}